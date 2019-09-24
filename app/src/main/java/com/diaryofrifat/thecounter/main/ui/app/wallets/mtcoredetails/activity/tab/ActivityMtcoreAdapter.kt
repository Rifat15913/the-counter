package com.diaryofrifat.thecounter.main.ui.app.wallets.mtcoredetails.activity.tab

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.remote.model.MtcoreWalletActivity
import com.diaryofrifat.thecounter.main.ui.base.callback.PaginationListener
import com.diaryofrifat.thecounter.main.ui.base.component.BaseAdapter
import com.diaryofrifat.thecounter.main.ui.base.component.BaseViewHolder
import com.diaryofrifat.thecounter.main.ui.base.makeItGone
import com.diaryofrifat.thecounter.main.ui.base.makeItVisible
import kotlinx.android.synthetic.main.item_mtcore_wallet_activity.view.*
import kotlinx.android.synthetic.main.item_pagination_footer.view.*

class ActivityMtcoreAdapter(paginationListener: PaginationListener) :
    BaseAdapter<MtcoreWalletActivity>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_FOOTER = 1
    }

    private val mPaginationListener = paginationListener

    override fun isEqual(
        left: MtcoreWalletActivity,
        right: MtcoreWalletActivity
    ): Boolean {
        return left.transactionID == right.transactionID
    }

    override fun newViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MtcoreWalletActivity> {
        return when (viewType) {
            TYPE_FOOTER -> {
                FooterViewHolder(inflate(parent, R.layout.item_pagination_footer))
            }

            else -> {
                ActivityViewHolder(inflate(parent, R.layout.item_mtcore_wallet_activity))
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
        holder: BaseViewHolder<MtcoreWalletActivity>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        if (holder is FooterViewHolder) {
            holder.bind()
        }
    }

    inner class ActivityViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<MtcoreWalletActivity>(viewDataBinding) {

        override fun bind(item: MtcoreWalletActivity) {
            itemView.text_view_first_field.text = item.time
            itemView.text_view_second_field.text = item.address
            itemView.text_view_third_field.text = item.amount
        }
    }

    inner class FooterViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<MtcoreWalletActivity>(viewDataBinding) {

        override fun bind(item: MtcoreWalletActivity) {

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