package com.itechsoftsolutions.mtcore.main.ui.app.ranking.myranking

import android.text.TextUtils
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareEntity
import com.itechsoftsolutions.mtcore.main.data.remote.model.MyRanking
import com.itechsoftsolutions.mtcore.main.ui.base.callback.PaginationListener
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseAdapter
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseViewHolder
import com.itechsoftsolutions.mtcore.main.ui.base.getString
import com.itechsoftsolutions.mtcore.main.ui.base.makeItGone
import com.itechsoftsolutions.mtcore.main.ui.base.makeItVisible
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import kotlinx.android.synthetic.main.item_my_ranking.view.*
import kotlinx.android.synthetic.main.item_pagination_footer.view.*
import java.util.*
import kotlin.collections.ArrayList

class MyRankingAdapter(paginationListener: PaginationListener) : BaseAdapter<MyRanking>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_FOOTER = 1
    }

    private val mPaginationListener = paginationListener
    var softwareList: List<SoftwareEntity> = ArrayList()

    override fun isEqual(left: MyRanking, right: MyRanking): Boolean {
        return false
    }

    override fun newViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MyRanking> {
        return when (viewType) {
            TYPE_FOOTER -> {
                FooterViewHolder(inflate(parent, R.layout.item_pagination_footer))
            }

            else -> {
                MyRankingViewHolder(inflate(parent, R.layout.item_my_ranking))
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

    override fun onBindViewHolder(holder: BaseViewHolder<MyRanking>, position: Int) {
        super.onBindViewHolder(holder, position)

        if (holder is FooterViewHolder) {
            holder.bind()
        }
    }

    inner class MyRankingViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<MyRanking>(viewDataBinding) {

        override fun bind(item: MyRanking) {
            var softwareName: String = Constants.Default.DEFAULT_STRING

            when (item.type) {
                MyRanking.SELLER -> {
                    for (i in softwareList.indices) {
                        val element = softwareList[i]

                        if (item.softwareID == element.softwareID) {
                            softwareName = element.title
                            break
                        }
                    }
                }

                MyRanking.CONSUMER -> {
                    if (item.productType == Constants.Common.PRODUCT_TYPE_SOFTWARE) {
                        for (i in softwareList.indices) {
                            val element = softwareList[i]

                            if (item.softwareID == element.softwareID) {
                                softwareName = element.title
                                break
                            }
                        }
                    } else {
                        softwareName = getString(R.string.my_rankings_bids)
                    }
                }

                else -> {
                    softwareName = if (!TextUtils.isEmpty(item.title)) {
                        item.title!!
                    } else {
                        getString(R.string.my_rankings_bids)
                    }
                }
            }

            itemView.text_view_title.text = softwareName
            itemView.text_view_time.text = item.time
            itemView.text_view_points.text =
                String.format(
                    Locale.getDefault(),
                    getString(R.string.ranking_list_points),
                    item.points.toString()
                )
        }
    }

    inner class FooterViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<MyRanking>(viewDataBinding) {

        override fun bind(item: MyRanking) {

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