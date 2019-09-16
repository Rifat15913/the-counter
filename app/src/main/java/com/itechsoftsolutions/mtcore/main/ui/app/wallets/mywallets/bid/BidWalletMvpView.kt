package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets.bid

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface BidWalletMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(title: String)
}