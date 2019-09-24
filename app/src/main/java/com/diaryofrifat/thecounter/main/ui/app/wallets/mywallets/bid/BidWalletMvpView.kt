package com.diaryofrifat.thecounter.main.ui.app.wallets.mywallets.bid

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface BidWalletMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(title: String)
}