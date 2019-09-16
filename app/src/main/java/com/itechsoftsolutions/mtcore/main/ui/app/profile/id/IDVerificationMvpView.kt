package com.itechsoftsolutions.mtcore.main.ui.app.profile.id

import com.itechsoftsolutions.mtcore.main.data.localandremote.model.user.UserEntity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface IDVerificationMvpView : MvpView {
    fun onError(message: String)
    fun onSuccess(message: String, type: Int)
    fun onGettingUser(user: UserEntity)
}