package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets.point

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface PointWalletMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(title: String)
    fun onSuccessfulWithdrawalRequest(message: String)
}