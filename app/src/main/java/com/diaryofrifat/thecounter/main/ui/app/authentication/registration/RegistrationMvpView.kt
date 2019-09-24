package com.diaryofrifat.thecounter.main.ui.app.authentication.registration

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface RegistrationMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
    fun onGettingCountryList(list: List<String>)
}