package com.itechsoftsolutions.mtcore.main.ui.app.wallets.biddetails.activity

import com.itechsoftsolutions.mtcore.main.data.remote.model.BidActivity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface BidActivityMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(list: List<BidActivity>)
}