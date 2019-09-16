package com.itechsoftsolutions.mtcore.main.ui.app.profile.bank

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.user.UserEntity
import com.itechsoftsolutions.mtcore.main.data.remote.model.BankWithPage
import com.itechsoftsolutions.mtcore.main.ui.base.callback.PaginationListener
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.initializeRecyclerView
import com.itechsoftsolutions.mtcore.main.ui.base.makeItGone
import com.itechsoftsolutions.mtcore.main.ui.base.makeItVisible
import com.itechsoftsolutions.mtcore.main.ui.base.setRipple
import com.itechsoftsolutions.mtcore.utils.helper.CountryUtils
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.custom_alert_dialog_for_adding_bank_account.view.*
import kotlinx.android.synthetic.main.fragment_profile_bank_details.*
import java.util.*

class BankDetailsFragment :
    BaseFragment<BankDetailsMvpView, BankDetailsPresenter>(),
    BankDetailsMvpView, PaginationListener {

    private var mDialogView: View? = null

    override val layoutId: Int
        get() = R.layout.fragment_profile_bank_details

    override fun getFragmentPresenter(): BankDetailsPresenter {
        return BankDetailsPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
    }

    private fun setListeners() {
        setClickListener(fab_add_bank)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun initialize() {
        initializeRecyclerView(
            recycler_view_bank,
            BankDetailsAdapter(this),
            null,
            null,
            LinearLayoutManager(mContext!!),
            null,
            null,
            null,
            null
        )
    }

    override fun stopUI() {

    }

    private fun getAdapter(): BankDetailsAdapter {
        return recycler_view_bank.adapter as BankDetailsAdapter
    }

    private fun loadData() {
        presenter.getUserBankList(
            mContext!!,
            DataUtils.getInteger(R.integer.page_one)
        )

        presenter.getUserFromCloud(
            mContext!!
        )
    }

    override fun onNextPage(currentPage: Int) {
        presenter.getUserBankList(
            mContext!!,
            (currentPage + 1)
        )
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onGettingBankList(list: List<BankWithPage>) {
        if (list.isEmpty() || list[list.size - 1].currentPage == DataUtils.getInteger(R.integer.page_one)) {
            getAdapter().clear()
        }

        if (list.isEmpty()) {
            text_view_empty_placeholder.makeItVisible()
        } else {
            text_view_empty_placeholder.makeItGone()
        }

        getAdapter().addItems(list)
        ProgressDialogUtils.on().hideProgressDialog()
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.fab_add_bank -> {
                showDialog()
                presenter.getCountryList()
                presenter.getUserFromCloud(mContext!!)
                presenter.getUserFromDatabase()
            }
        }
    }

    fun showDialog() {
        val builder = AlertDialog.Builder(mContext!!)
        builder.setCancelable(false)

        val dialogView = LayoutInflater.from(mContext!!)
            .inflate(R.layout.custom_alert_dialog_for_adding_bank_account, null, false)
        mDialogView = dialogView
        builder.setView(dialogView)

        dialogView.text_view_add.setRipple(R.color.colorWhite26)
        dialogView.text_view_cancel.setRipple(R.color.colorWhite26)

        val dialog = builder.create()

        dialogView.text_view_cancel.setOnClickListener {
            dialog?.dismiss()
        }

        dialogView.text_view_add.setOnClickListener {
            if (dialogView.checkbox_caution.isChecked
                && !TextUtils.isEmpty(dialogView.edit_text_bank_name.text)
                && !TextUtils.isEmpty(dialogView.edit_text_bank_address.text)
                && !TextUtils.isEmpty(dialogView.edit_text_bank_swift_code.text)
                && !TextUtils.isEmpty(dialogView.edit_text_account_holder_name.text)
                && !TextUtils.isEmpty(dialogView.edit_text_account_holder_address.text)
                && !TextUtils.isEmpty(dialogView.edit_text_account_holder_iban.text)
            ) {
                presenter.addBankAccount(
                    mContext!!,
                    dialogView.edit_text_bank_name.text.toString().trim(),
                    dialogView.edit_text_bank_address.text.toString().trim(),
                    CountryUtils.getCountryNames()[dialogView.spinner_bank_country.selectedItemPosition],
                    dialogView.edit_text_bank_swift_code.text.toString().trim(),
                    dialogView.edit_text_account_holder_name.text.toString().trim(),
                    dialogView.edit_text_account_holder_address.text.toString().trim(),
                    dialogView.edit_text_account_holder_iban.text.toString().trim(),
                    if (dialogView.checkbox_caution.isChecked) 1.toString() else 0.toString()
                )

                dialog?.dismiss()
            } else {
                onError(getString(R.string.login_error_valid_fields))
            }
        }

        dialog?.show()
    }

    override fun onAddition(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
        loadData()
    }

    override fun onGettingUser(user: UserEntity) {
        mDialogView?.edit_text_account_holder_name?.setText(
            String.format(
                Locale.getDefault(),
                getString(R.string.profile_full_name),
                user.firstName, user.lastName
            )
        )
    }

    override fun onGettingCountryList(list: List<String>) {
        val spinnerAdapter = ArrayAdapter(
            mContext!!,
            R.layout.spinner_custom_item5,
            list
        )

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_custom_dark_item5)
        mDialogView?.spinner_bank_country?.adapter = spinnerAdapter
    }
}