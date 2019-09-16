package com.itechsoftsolutions.mtcore.main.ui.app.software.history

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareEntity
import com.itechsoftsolutions.mtcore.main.data.remote.model.SoftwareLicensePurchaseHistory
import com.itechsoftsolutions.mtcore.main.ui.base.callback.PaginationListener
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseAdapter
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseViewHolder
import com.itechsoftsolutions.mtcore.main.ui.base.makeItGone
import com.itechsoftsolutions.mtcore.main.ui.base.makeItVisible
import com.itechsoftsolutions.mtcore.main.ui.base.toTitleCase
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.TimeUtils
import kotlinx.android.synthetic.main.item_pagination_footer.view.*
import kotlinx.android.synthetic.main.item_software_license_purchase_history.view.*

class SoftwareLicensePurchaseHistoryAdapter(paginationListener: PaginationListener) :
    BaseAdapter<SoftwareLicensePurchaseHistory>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_FOOTER = 1
    }

    private val mPaginationListener = paginationListener
    var softwareList: List<SoftwareEntity> = ArrayList()

    override fun isEqual(
        left: SoftwareLicensePurchaseHistory,
        right: SoftwareLicensePurchaseHistory
    ): Boolean {
        return left.id == right.id
    }

    override fun newViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SoftwareLicensePurchaseHistory> {
        return when (viewType) {
            TYPE_FOOTER -> {
                FooterViewHolder(inflate(parent, R.layout.item_pagination_footer))
            }

            else -> {
                SoftwareViewHolder(inflate(parent, R.layout.item_software_license_purchase_history))
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
        holder: BaseViewHolder<SoftwareLicensePurchaseHistory>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        if (holder is FooterViewHolder) {
            holder.bind()
        }
    }

    inner class SoftwareViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<SoftwareLicensePurchaseHistory>(viewDataBinding) {

        override fun bind(item: SoftwareLicensePurchaseHistory) {
            var softwareName: String = Constants.Default.DEFAULT_STRING

            for (i in softwareList.indices) {
                val element = softwareList[i]

                if (item.softwareID == element.softwareID) {
                    softwareName = element.title
                    break
                }
            }

            itemView.text_view_software_name.text = softwareName
            itemView.text_view_price.text = item.price.toString()

            item.status?.let {
                itemView.text_view_status.text = it.toTitleCase()
            }

            item.time?.let {
                itemView.text_view_date.text =
                    TimeUtils.getFormattedOnlyDateString(TimeUtils.getCalendarFromDate(it).timeInMillis)
            }
        }
    }

    inner class FooterViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<SoftwareLicensePurchaseHistory>(viewDataBinding) {

        override fun bind(item: SoftwareLicensePurchaseHistory) {

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