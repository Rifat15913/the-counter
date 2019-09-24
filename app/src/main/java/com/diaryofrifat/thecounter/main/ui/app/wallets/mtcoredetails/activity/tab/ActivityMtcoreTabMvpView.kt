package com.diaryofrifat.thecounter.main.ui.app.wallets.mtcoredetails.activity.tab

import com.diaryofrifat.thecounter.main.data.remote.model.MtcoreWalletActivity
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface ActivityMtcoreTabMvpView : MvpView {
    fun onError(message: String)
    fun onGettingHistory(list: List<MtcoreWalletActivity>)
}