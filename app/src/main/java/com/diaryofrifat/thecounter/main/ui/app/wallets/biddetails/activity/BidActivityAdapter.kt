package com.diaryofrifat.thecounter.main.ui.app.wallets.biddetails.activity

import android.text.TextUtils
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.remote.model.BidActivity
import com.diaryofrifat.thecounter.main.ui.base.callback.PaginationListener
import com.diaryofrifat.thecounter.main.ui.base.component.BaseAdapter
import com.diaryofrifat.thecounter.main.ui.base.component.BaseViewHolder
import com.diaryofrifat.thecounter.main.ui.base.makeItGone
import com.diaryofrifat.thecounter.main.ui.base.makeItVisible
import kotlinx.android.synthetic.main.item_bid_activity.view.*
import kotlinx.android.synthetic.main.item_pagination_footer.view.*

class BidActivityAdapter(paginationListener: PaginationListener) :
    BaseAdapter<BidActivity>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_FOOTER = 1
    }

    private val mPaginationListener = paginationListener

    override fun isEqual(left: BidActivity, right: BidActivity): Boolean {
        return left.id == right.id
    }

    override fun newViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BidActivity> {
        return when (viewType) {
            TYPE_FOOTER -> {
                FooterViewHolder(inflate(parent, R.layout.item_pagination_footer))
            }

            else -> {
                SoftwareViewHolder(inflate(parent, R.layout.item_bid_activity))
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
        holder: BaseViewHolder<BidActivity>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        if (holder is FooterViewHolder) {
            holder.bind()
        }
    }

    inner class SoftwareViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<BidActivity>(viewDataBinding) {

        override fun bind(item: BidActivity) {
            if (!TextUtils.isEmpty(item.time)) {
                itemView.text_view_time.text = item.time
            }

            if (!TextUtils.isEmpty(item.amount)) {
                itemView.text_view_amount.text = item.amount
            }
        }
    }

    inner class FooterViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<BidActivity>(viewDataBinding) {

        override fun bind(item: BidActivity) {

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