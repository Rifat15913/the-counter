package com.itechsoftsolutions.mtcore.main.ui.app.authentication.login

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface LoginMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}