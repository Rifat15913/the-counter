package com.diaryofrifat.thecounter.main.ui.app.settings.container

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.BaseRepository
import com.diaryofrifat.thecounter.main.data.remote.model.Language
import com.diaryofrifat.thecounter.main.ui.app.landing.container.ContainerActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BasePresenter
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.helper.LanguageUtils
import com.diaryofrifat.thecounter.utils.helper.SharedPrefUtils
import com.diaryofrifat.thecounter.utils.helper.network.NoConnectivityException
import com.diaryofrifat.thecounter.utils.libs.GsonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class SettingsPresenter : BasePresenter<SettingsMvpView>() {
    var languageList: MutableList<Language> = ArrayList()

    fun setTwoFactorAuthentication(context: Context) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().setTwoFactorAuthentication()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    dialog?.dismiss()
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response != null) {
                            if (response.isSuccessful) {
                                mvpView?.onSuccess(response.message)
                            } else {
                                mvpView?.onError(response.message)
                            }
                        } else {
                            mvpView?.onError(DataUtils.getString(R.string.settings_error_set_2_factor_authentication))
                        }
                    }
                }, {
                    dialog?.dismiss()
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.settings_error_set_2_factor_authentication))
                    }
                })
        )
    }

    fun getSettings(context: Context) {
        compositeDisposable.add(
            BaseRepository.on().getSettings()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response != null && response.isSuccessful) {
                            if (response.data
                                    .asJsonObject
                                    .has(Constants.JsonKeys.USER_SETTINGS)
                                && !(response.data
                                    .asJsonObject[Constants.JsonKeys.USER_SETTINGS]
                                    .isJsonNull)
                            ) {
                                SharedPrefUtils.write(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON,
                                    response.data
                                        .asJsonObject[Constants.JsonKeys.USER_SETTINGS]
                                        .asJsonObject[Constants.JsonKeys.IS_GOOGLE_AUTH_ENABLED_AND_ON]
                                        .asInt.let { value ->
                                        value == 1
                                    }
                                )

                                SharedPrefUtils.write(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET,
                                    response.data
                                        .asJsonObject[Constants.JsonKeys.USER_SETTINGS]
                                        .asJsonObject[Constants.JsonKeys.GOOGLE_AUTH_ENABLED]
                                        .asInt.let { value ->
                                        value == 1
                                    }
                                )

                                val languageCodeFromServer = response.data
                                    .asJsonObject[Constants.JsonKeys.USER_SETTINGS]
                                    .asJsonObject[Constants.JsonKeys.LANGUAGE]
                                    .asString

                                if (LanguageUtils.getLanguage() != languageCodeFromServer) {
                                    LanguageUtils.setLanguageAndRestartApplication(
                                        Intent(context, ContainerActivity::class.java),
                                        context,
                                        languageCodeFromServer
                                    )
                                }
                            }

                            languageList.clear()

                            response.data
                                .asJsonObject
                                .getAsJsonArray(Constants.JsonKeys.LANGUAGES)
                                .forEach { element ->
                                    languageList.add(GsonUtils.fromJson<Language>(element.toString())!!)
                                }

                            var currentLanguage = Constants.Default.DEFAULT_STRING

                            for (i in languageList.indices) {
                                val language = languageList[i]

                                if (language.code == LanguageUtils.getLanguage()) {
                                    currentLanguage = language.language
                                    break
                                }
                            }

                            mvpView?.onGettingSettings(currentLanguage)
                        }
                    }
                }, {
                    Timber.d(it)
                })
        )
    }

    fun setLanguage(context: Context, language: String) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)
        var desiredLanguageCode: String? = null

        languageList.forEach {
            if (it.language == language) {
                desiredLanguageCode = it.code
            }
        }

        if (!TextUtils.isEmpty(desiredLanguageCode)) {
            compositeDisposable.add(
                BaseRepository.on().setLanguage(desiredLanguageCode!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        dialog?.dismiss()
                        if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                        } else {
                            val response = it.body()

                            if (response != null) {
                                if (response.isSuccessful) {
                                    mvpView?.onSettingLanguage(response.message)

                                    if (desiredLanguageCode != LanguageUtils.getLanguage()) {
                                        LanguageUtils.setLanguageAndRestartApplication(
                                            Intent(context, ContainerActivity::class.java),
                                            context,
                                            desiredLanguageCode!!
                                        )
                                    }
                                } else {
                                    mvpView?.onSettingLanguageError(response.message)
                                }
                            } else {
                                mvpView?.onSettingLanguageError(DataUtils.getString(R.string.settings_error_could_not_set_language))
                            }
                        }
                    }, {
                        dialog?.dismiss()
                        Timber.d(it)

                        if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                            mvpView?.onSettingLanguageError(it.message)
                        } else {
                            mvpView?.onSettingLanguageError(DataUtils.getString(R.string.settings_error_could_not_set_language))
                        }
                    })
            )
        }
    }
}