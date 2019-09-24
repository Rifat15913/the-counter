package com.diaryofrifat.thecounter.main.ui.app.landing.dashboard

import com.diaryofrifat.thecounter.main.data.local.model.DashboardItem
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface DashboardMvpView : MvpView {
    fun onResult()
    fun onSuccess(list: List<DashboardItem>)
    fun onError(message: String)
}