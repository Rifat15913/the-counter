package com.diaryofrifat.thecounter.main.ui.app.profile.container

import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserEntity
import com.diaryofrifat.thecounter.main.data.remote.model.ProfileBadge
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface ProfileContainerMvpView : MvpView {
    fun onError(message: String)
    fun onGettingProfileBadgeList(list: List<ProfileBadge>)
    fun onGettingUser(user: UserEntity)
    fun onUploadingProfilePicture(profilePictureUrl: String)
}