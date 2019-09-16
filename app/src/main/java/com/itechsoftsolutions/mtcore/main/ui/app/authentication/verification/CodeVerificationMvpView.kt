package com.itechsoftsolutions.mtcore.main.ui.app.authentication.verification

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface CodeVerificationMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}