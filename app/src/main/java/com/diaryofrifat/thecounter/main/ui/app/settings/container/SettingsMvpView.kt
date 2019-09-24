package com.diaryofrifat.thecounter.main.ui.app.settings.container

import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface SettingsMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
    fun onSettingLanguageError(message: String)
    fun onSettingLanguage(message: String)
    fun onGettingSettings(language: String)
}