package com.diaryofrifat.thecounter.main.ui.app.wallets.biddetails.activity

import com.diaryofrifat.thecounter.main.data.remote.model.BidActivity
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface BidActivityMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(list: List<BidActivity>)
}