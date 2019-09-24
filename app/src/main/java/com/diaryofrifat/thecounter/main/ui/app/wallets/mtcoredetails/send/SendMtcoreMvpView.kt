package com.diaryofrifat.thecounter.main.ui.app.wallets.mtcoredetails.send

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface SendMtcoreMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(
        walletAddress: String,
        walletID: String,
        amount: String,
        note: String?
    )
}