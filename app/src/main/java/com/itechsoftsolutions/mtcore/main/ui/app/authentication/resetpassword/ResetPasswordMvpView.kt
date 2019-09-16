package com.itechsoftsolutions.mtcore.main.ui.app.authentication.resetpassword

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface ResetPasswordMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}