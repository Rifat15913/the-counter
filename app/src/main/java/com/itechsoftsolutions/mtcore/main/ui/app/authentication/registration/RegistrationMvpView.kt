package com.itechsoftsolutions.mtcore.main.ui.app.authentication.registration

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface RegistrationMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
    fun onGettingCountryList(list: List<String>)
}