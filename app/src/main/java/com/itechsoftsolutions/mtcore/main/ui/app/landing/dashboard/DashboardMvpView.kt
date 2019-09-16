package com.itechsoftsolutions.mtcore.main.ui.app.landing.dashboard

import com.itechsoftsolutions.mtcore.main.data.local.model.DashboardItem
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface DashboardMvpView : MvpView {
    fun onResult()
    fun onSuccess(list: List<DashboardItem>)
    fun onError(message: String)
}