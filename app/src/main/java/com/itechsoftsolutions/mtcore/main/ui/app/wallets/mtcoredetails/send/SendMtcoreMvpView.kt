package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mtcoredetails.send

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface SendMtcoreMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(
        walletAddress: String,
        walletID: String,
        amount: String,
        note: String?
    )
}