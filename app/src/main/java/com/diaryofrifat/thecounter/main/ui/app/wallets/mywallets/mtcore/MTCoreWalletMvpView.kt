package com.diaryofrifat.thecounter.main.ui.app.wallets.mywallets.mtcore

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface MTCoreWalletMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(title: String)
}