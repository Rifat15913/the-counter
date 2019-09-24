package com.diaryofrifat.thecounter.main.ui.app.wallets.mtcoredetails.receive

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface ReceiveMtcoreMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(address: String)
}