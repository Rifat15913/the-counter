package com.diaryofrifat.thecounter.main.ui.app.wallets.pointdetails.activity

import com.diaryofrifat.thecounter.main.data.remote.model.PointActivity
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface PointActivityMvpView : MvpView {
    fun onError(message: String)
    fun onConfirmation(message: String, item: PointActivity)
    fun onSuccess(list: List<PointActivity>)
}