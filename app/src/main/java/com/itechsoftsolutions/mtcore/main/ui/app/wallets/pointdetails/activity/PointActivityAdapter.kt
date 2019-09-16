package com.itechsoftsolutions.mtcore.main.ui.app.wallets.pointdetails.activity

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.remote.model.PointActivity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.PaginationListener
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseAdapter
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseViewHolder
import com.itechsoftsolutions.mtcore.main.ui.base.getString
import com.itechsoftsolutions.mtcore.main.ui.base.makeItGone
import com.itechsoftsolutions.mtcore.main.ui.base.makeItVisible
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.libs.GlideUtils
import kotlinx.android.synthetic.main.item_pagination_footer.view.*
import kotlinx.android.synthetic.main.item_point_activity.view.*
import java.util.*

class PointActivityAdapter(paginationListener: PaginationListener) :
    BaseAdapter<PointActivity>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_FOOTER = 1
    }

    private val mPaginationListener = paginationListener

    override fun isEqual(left: PointActivity, right: PointActivity): Boolean {
        return left.id == right.id
    }

    override fun newViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<PointActivity> {
        return when (viewType) {
            TYPE_FOOTER -> {
                FooterViewHolder(inflate(parent, R.layout.item_pagination_footer))
            }

            else -> {
                PointHistoryViewHolder(inflate(parent, R.layout.item_point_activity))
            }
        }
    }

    override fun getItemCount(): Int {
        return mItemList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionFooter(position)) {
            TYPE_FOOTER
        } else TYPE_ITEM
    }

    private fun isPositionFooter(position: Int): Boolean {
        return position == mItemList.size
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<PointActivity>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        if (holder is FooterViewHolder) {
            holder.bind()
        }
    }

    inner class PointHistoryViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<PointActivity>(viewDataBinding) {

        override fun bind(item: PointActivity) {
            itemView.text_view_point.text =
                String.format(
                    Locale.getDefault(),
                    getString(R.string.point_activity_point),
                    item.point
                )

            itemView.text_view_cash.text =
                String.format(
                    Locale.getDefault(),
                    getString(R.string.point_activity_cash),
                    item.cash
                )

            itemView.text_view_bank_details.text =
                String.format(
                    Locale.getDefault(),
                    getString(R.string.point_activity_bank),
                    item.bankName
                )

            if (TextUtils.isEmpty(item.adminMessage)) {
                itemView.text_view_admin_message.makeItGone()
            } else {
                itemView.text_view_admin_message.makeItVisible()
                itemView.text_view_admin_message.text =
                    String.format(
                        Locale.getDefault(),
                        getString(R.string.point_activity_admin_message),
                        item.adminMessage
                    )
            }

            GlideUtils.custom(
                itemView.image_view_bank_receipt,
                item.bankReceiptImageUrl,
                RequestOptions()
                    .placeholder(R.drawable.ic_generic_photo_placeholder_small)
                    .error(R.drawable.ic_generic_photo_placeholder_small)
                    .centerCrop()
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC),
                null
            )

            if (!TextUtils.isEmpty(item.bankReceiptImageUrl)) {
                setClickListener(itemView.image_view_bank_receipt)
            }

            when (item.status) {
                Constants.Common.STATUS_USER_PENDING -> {
                    itemView.view_separator.makeItVisible()
                    itemView.text_view_approve.makeItVisible()

                    setClickListener(itemView.text_view_approve)

                    itemView.text_view_status.text =
                        String.format(
                            Locale.getDefault(),
                            getString(R.string.point_activity_status),
                            getString(R.string.point_activity_pending)
                        )
                }

                Constants.Common.STATUS_ADMIN_PENDING -> {
                    itemView.view_separator.makeItGone()
                    itemView.text_view_approve.makeItGone()

                    itemView.text_view_status.text =
                        String.format(
                            Locale.getDefault(),
                            getString(R.string.point_activity_status),
                            getString(R.string.point_activity_pending)
                        )
                }

                else -> {
                    itemView.view_separator.makeItGone()
                    itemView.text_view_approve.makeItGone()

                    itemView.text_view_status.text =
                        String.format(
                            Locale.getDefault(),
                            getString(R.string.point_activity_status),
                            getString(R.string.point_activity_confirm)
                        )
                }
            }
        }

        override fun onClick(view: View) {
            super.onClick(view)

            getItem(adapterPosition)?.let {
                mItemClickListener?.onItemClick(view, it)
            }
        }
    }

    inner class FooterViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<PointActivity>(viewDataBinding) {

        override fun bind(item: PointActivity) {

        }

        fun bind() {
            if (getItems().isEmpty()) {
                itemView.progress_bar.makeItGone()
            } else {
                getItem(getItems().size - 1)?.let {
                    if (it.currentPage != it.lastPage) {
                        itemView.progress_bar.makeItVisible()
                        mPaginationListener.onNextPage(it.currentPage)
                    } else {
                        itemView.progress_bar.makeItGone()
                    }
                }
            }
        }
    }
}