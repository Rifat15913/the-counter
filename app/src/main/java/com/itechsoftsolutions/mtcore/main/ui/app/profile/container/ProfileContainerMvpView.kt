package com.itechsoftsolutions.mtcore.main.ui.app.profile.container

import com.itechsoftsolutions.mtcore.main.data.localandremote.model.user.UserEntity
import com.itechsoftsolutions.mtcore.main.data.remote.model.ProfileBadge
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface ProfileContainerMvpView : MvpView {
    fun onError(message: String)
    fun onGettingProfileBadgeList(list: List<ProfileBadge>)
    fun onGettingUser(user: UserEntity)
    fun onUploadingProfilePicture(profilePictureUrl: String)
}