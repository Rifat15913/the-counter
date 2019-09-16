package com.itechsoftsolutions.mtcore.main.ui.app.authentication.forgotpassword

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface ForgotPasswordMvpView : MvpView {
    fun onSuccess(message: String, token: String)
    fun onError(message: String)
}


