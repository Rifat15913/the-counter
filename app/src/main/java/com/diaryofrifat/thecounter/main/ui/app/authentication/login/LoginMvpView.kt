package com.diaryofrifat.thecounter.main.ui.app.authentication.login

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface LoginMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}