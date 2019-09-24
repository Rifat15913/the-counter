package com.diaryofrifat.thecounter.main.ui.app.authentication.resetpassword

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface ResetPasswordMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}