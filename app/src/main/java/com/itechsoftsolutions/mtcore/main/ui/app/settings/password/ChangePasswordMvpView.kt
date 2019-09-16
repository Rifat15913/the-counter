package com.itechsoftsolutions.mtcore.main.ui.app.settings.password

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface ChangePasswordMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}