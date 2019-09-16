package com.itechsoftsolutions.mtcore.main.ui.app.profile.bank

import android.text.TextUtils
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.remote.model.BankWithPage
import com.itechsoftsolutions.mtcore.main.ui.base.callback.PaginationListener
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseAdapter
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseViewHolder
import com.itechsoftsolutions.mtcore.main.ui.base.getString
import com.itechsoftsolutions.mtcore.main.ui.base.makeItGone
import com.itechsoftsolutions.mtcore.main.ui.base.makeItVisible
import kotlinx.android.synthetic.main.item_pagination_footer.view.*
import kotlinx.android.synthetic.main.item_profile_bank.view.*
import java.util.*

class BankDetailsAdapter(paginationListener: PaginationListener) :
    BaseAdapter<BankWithPage>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_FOOTER = 1
    }

    private val mPaginationListener = paginationListener

    override fun isEqual(
        left: BankWithPage,
        right: BankWithPage
    ): Boolean {
        return left.bankAccountID == right.bankAccountID
    }

    override fun newViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BankWithPage> {
        return when (viewType) {
            TYPE_FOOTER -> {
                FooterViewHolder(inflate(parent, R.layout.item_pagination_footer))
            }

            else -> {
                BankViewHolder(inflate(parent, R.layout.item_profile_bank))
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
        holder: BaseViewHolder<BankWithPage>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        if (holder is FooterViewHolder) {
            holder.bind()
        }
    }

    inner class BankViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<BankWithPage>(viewDataBinding) {

        override fun bind(item: BankWithPage) {
            itemView.text_view_name.text =
                String.format(
                    Locale.getDefault(),
                    getString(R.string.profile_bank_name),
                    item.bankName
                )

            if (!TextUtils.isEmpty(item.bankAddress.toString().trim())) {
                itemView.text_view_address.makeItVisible()
                itemView.text_view_address.text =
                    String.format(
                        Locale.getDefault(),
                        getString(R.string.profile_bank_address),
                        item.bankAddress
                    )
            } else {
                itemView.text_view_address.makeItGone()
            }

            if (!TextUtils.isEmpty(item.bankCountry.toString().trim())) {
                itemView.text_view_country.makeItVisible()
                itemView.text_view_country.text =
                    String.format(
                        Locale.getDefault(),
                        getString(R.string.profile_country),
                        item.bankCountry
                    )
            } else {
                itemView.text_view_country.makeItGone()
            }

            if (!TextUtils.isEmpty(item.bankSwiftCode.toString().trim())) {
                itemView.text_view_swift_code.makeItVisible()
                itemView.text_view_swift_code.text =
                    String.format(
                        Locale.getDefault(),
                        getString(R.string.profile_swift_code),
                        item.bankSwiftCode
                    )
            } else {
                itemView.text_view_swift_code.makeItGone()
            }

            if (!TextUtils.isEmpty(item.holderIBAN.toString().trim())) {
                itemView.text_view_account_holder_iban.makeItVisible()
                itemView.text_view_account_holder_iban.text =
                    String.format(
                        Locale.getDefault(),
                        getString(R.string.profile_account_holder_iban),
                        item.holderIBAN
                    )
            } else {
                itemView.text_view_account_holder_iban.makeItGone()
            }

            if (!TextUtils.isEmpty(item.holderName.toString().trim())) {
                itemView.text_view_account_holder_name.makeItVisible()
                itemView.text_view_account_holder_name.text =
                    String.format(
                        Locale.getDefault(),
                        getString(R.string.profile_account_holder_name),
                        item.holderName
                    )
            } else {
                itemView.text_view_account_holder_name.makeItGone()
            }

            if (!TextUtils.isEmpty(item.holderAddress.toString().trim())) {
                itemView.text_view_account_holder_address.makeItVisible()
                itemView.text_view_account_holder_address.text =
                    String.format(
                        Locale.getDefault(),
                        getString(R.string.profile_account_holder_address),
                        item.holderAddress
                    )
            } else {
                itemView.text_view_account_holder_address.makeItGone()
            }
        }
    }

    inner class FooterViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<BankWithPage>(viewDataBinding) {

        override fun bind(item: BankWithPage) {

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