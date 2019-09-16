package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets.mtcore

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface MTCoreWalletMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(title: String)
}