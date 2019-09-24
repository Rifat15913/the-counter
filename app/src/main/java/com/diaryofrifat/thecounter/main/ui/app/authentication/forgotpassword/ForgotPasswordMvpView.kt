package com.diaryofrifat.thecounter.main.ui.app.authentication.forgotpassword

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface ForgotPasswordMvpView : MvpView {
    fun onSuccess(message: String, token: String)
    fun onError(message: String)
}


