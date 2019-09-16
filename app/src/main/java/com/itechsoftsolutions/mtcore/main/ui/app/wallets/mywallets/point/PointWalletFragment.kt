package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets.point

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.app.wallets.pointdetails.activity.PointHistoryActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.setRipple
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.custom_alert_dialog_for_id_verification.view.text_view_cancel
import kotlinx.android.synthetic.main.custom_alert_dialog_for_withdrawal_of_point.view.*
import kotlinx.android.synthetic.main.fragment_point_wallet.*

class PointWalletFragment : BaseFragment<PointWalletMvpView, PointWalletPresenter>(),
    PointWalletMvpView {

    private var mWalletBalance: String? = null

    override val layoutId: Int
        get() = R.layout.fragment_point_wallet

    override fun getFragmentPresenter(): PointWalletPresenter {
        return PointWalletPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
        loadData()
    }

    private fun setListeners() {
        setClickListener(text_view_receive, text_view_activity)
    }

    private fun initialize() {
        text_view_receive.setRipple(R.color.colorWhite50)
        text_view_activity.setRipple(R.color.colorWhite50)
    }

    private fun loadData() {
        presenter.getWalletBalance(mContext!!)
    }

    override fun stopUI() {

    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onSuccess(title: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        text_view_balance.text = title
        mWalletBalance = title
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.text_view_activity -> {
                if (!TextUtils.isEmpty(mWalletBalance)) {
                    PointHistoryActivity.startActivity(mContext!!, mWalletBalance!!)
                }
            }

            R.id.text_view_receive -> {
                if (presenter.mBankAccountList.isEmpty()) {
                    ToastUtils.warning(getString(R.string.my_wallets_point_no_approved_bank_found))
                } else {
                    showDialog()
                }
            }
        }
    }

    fun showDialog() {
        val builder = AlertDialog.Builder(mContext!!)
        builder.setCancelable(false)

        val dialogView = LayoutInflater.from(mContext!!)
            .inflate(R.layout.custom_alert_dialog_for_withdrawal_of_point, null, false)
        builder.setView(dialogView)

        dialogView.text_view_withdraw.setRipple(R.color.colorWhite26)
        dialogView.text_view_cancel.setRipple(R.color.colorWhite26)

        val bankNameList: MutableList<String> = ArrayList()

        presenter.mBankAccountList.forEach {
            bankNameList.add(it.bankName)
        }

        val spinnerAdapter = ArrayAdapter(
            mContext!!,
            R.layout.spinner_custom_item4,
            bankNameList
        )

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_custom_dark_item4)
        dialogView.spinner_bank.adapter = spinnerAdapter

        val dialog = builder.create()

        dialogView.text_view_cancel.setOnClickListener {
            dialog?.dismiss()
        }

        dialogView.text_view_withdraw.setOnClickListener {
            if (presenter.mBankAccountList.isNotEmpty()
                && !(TextUtils.isEmpty(dialogView.edit_text_amount.text))
            ) {
                presenter.requestPointWithdrawal(
                    mContext!!,
                    presenter.mBankAccountList[dialogView.spinner_bank.selectedItemPosition].bankID,
                    dialogView.edit_text_amount.text.toString().trim()
                )

                dialog?.dismiss()
            } else {
                onError(getString(R.string.buy_software_error_could_not_place_order))
            }
        }

        dialog?.show()
    }

    override fun onSuccessfulWithdrawalRequest(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
    }

}