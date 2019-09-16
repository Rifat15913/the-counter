package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets.mtcore

import android.text.TextUtils
import android.view.View
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.app.wallets.mtcoredetails.container.MtcoreDetailsContainerActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.setRipple
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_mtr_wallet.*

class MTCoreWalletFragment : BaseFragment<MTCoreWalletMvpView, MTCoreWalletPresenter>(),
    MTCoreWalletMvpView {

    override val layoutId: Int
        get() = R.layout.fragment_mtr_wallet

    override fun getFragmentPresenter(): MTCoreWalletPresenter {
        return MTCoreWalletPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
        loadData()
    }

    private fun setListeners() {
        setClickListener(text_view_receive, text_view_activity, text_view_send)
    }

    private fun initialize() {
        text_view_receive.setRipple(R.color.colorWhite50)
        text_view_activity.setRipple(R.color.colorWhite50)
        text_view_send.setRipple(R.color.colorWhite50)
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
    }

    override fun onClick(view: View) {
        super.onClick(view)

        if (!TextUtils.isEmpty(presenter.walletID)
            && !TextUtils.isEmpty(text_view_balance.text)
        ) {
            MtcoreDetailsContainerActivity.startActivity(
                mContext!!,
                presenter.walletID!!,
                view.id,
                text_view_balance.text.toString().trim()
            )
        } else {
            onError(getString(R.string.mtr_wallet_error_go_back_try_again))
        }
    }
}