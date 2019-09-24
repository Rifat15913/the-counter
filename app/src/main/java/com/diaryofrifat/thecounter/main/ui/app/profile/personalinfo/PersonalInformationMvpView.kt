package com.diaryofrifat.thecounter.main.ui.app.profile.personalinfo

import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserEntity
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface PersonalInformationMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(message: String)
    fun onGettingUser(user: UserEntity)
    fun onGettingCountryList(list: List<String>)
}