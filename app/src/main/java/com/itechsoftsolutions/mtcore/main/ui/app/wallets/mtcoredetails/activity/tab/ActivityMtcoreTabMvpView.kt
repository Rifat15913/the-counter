package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mtcoredetails.activity.tab

import com.itechsoftsolutions.mtcore.main.data.remote.model.MtcoreWalletActivity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface ActivityMtcoreTabMvpView : MvpView {
    fun onError(message: String)
    fun onGettingHistory(list: List<MtcoreWalletActivity>)
}