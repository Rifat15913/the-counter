package com.diaryofrifat.thecounter.main.ui.app.profile.id

import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserEntity
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface IDVerificationMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(message: String, type: Int)
    fun onGettingUser(user: UserEntity)
}