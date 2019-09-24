package com.diaryofrifat.thecounter.main.ui.app.authentication.verification

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface CodeVerificationMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}