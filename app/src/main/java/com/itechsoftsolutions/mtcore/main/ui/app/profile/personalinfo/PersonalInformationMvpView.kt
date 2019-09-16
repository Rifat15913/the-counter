package com.itechsoftsolutions.mtcore.main.ui.app.profile.personalinfo

import com.itechsoftsolutions.mtcore.main.data.localandremote.model.user.UserEntity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface PersonalInformationMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(message: String)
    fun onGettingUser(user: UserEntity)
    fun onGettingCountryList(list: List<String>)
}