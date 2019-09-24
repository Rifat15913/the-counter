package com.diaryofrifat.thecounter.main.ui.app.myreferral

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface MyReferralMvpView : MvpView {
    fun onSuccess(url: String)
    fun onError(message: String)
}