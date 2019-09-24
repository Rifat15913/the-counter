package com.diaryofrifat.thecounter.main.ui.app.profile.activity

import com.diaryofrifat.thecounter.main.data.remote.model.ProfileActivity
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface ProfileActivityMvpView : MvpView {
    fun onError(message: String)
    fun onGettingHistory(list: List<ProfileActivity>)
}