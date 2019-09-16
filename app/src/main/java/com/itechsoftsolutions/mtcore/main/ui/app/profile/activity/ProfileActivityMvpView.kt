package com.itechsoftsolutions.mtcore.main.ui.app.profile.activity

import com.itechsoftsolutions.mtcore.main.data.remote.model.ProfileActivity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface ProfileActivityMvpView : MvpView {
    fun onError(message: String)
    fun onGettingHistory(list: List<ProfileActivity>)
}