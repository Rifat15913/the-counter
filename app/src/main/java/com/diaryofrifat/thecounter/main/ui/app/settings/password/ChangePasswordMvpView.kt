package com.diaryofrifat.thecounter.main.ui.app.settings.password

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface ChangePasswordMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}