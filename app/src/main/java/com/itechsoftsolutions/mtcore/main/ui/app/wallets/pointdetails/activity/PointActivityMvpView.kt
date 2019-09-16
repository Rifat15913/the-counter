package com.itechsoftsolutions.mtcore.main.ui.app.wallets.pointdetails.activity

import com.itechsoftsolutions.mtcore.main.data.remote.model.PointActivity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface PointActivityMvpView : MvpView {
    fun onError(message: String)
    fun onConfirmation(message: String, item: PointActivity)
    fun onSuccess(list: List<PointActivity>)
}