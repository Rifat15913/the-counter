package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mtcoredetails.receive

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface ReceiveMtcoreMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(address: String)
}