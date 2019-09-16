package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets.bid

import android.text.TextUtils
import android.view.View
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.app.wallets.biddetails.activity.BidHistoryActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.setRipple
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_bid_wallet.*

class BidWalletFragment : BaseFragment<BidWalletMvpView, BidWalletPresenter>(),
    BidWalletMvpView {

    private var mWalletBalance: String? = null

    override val layoutId: Int
        get() = R.layout.fragment_bid_wallet

    override fun getFragmentPresenter(): BidWalletPresenter {
        return BidWalletPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
        loadData()
    }

    private fun setListeners() {
        setClickListener(text_view_activity)
    }

    private fun initialize() {
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
                    BidHistoryActivity.startActivity(
                        mContext!!,
                        mWalletBalance!!
                    )
                }
            }
        }
    }
}