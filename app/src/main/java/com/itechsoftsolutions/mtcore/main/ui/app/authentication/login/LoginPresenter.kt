package com.itechsoftsolutions.mtcore.main.ui.app.authentication.login

import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.SharedPrefUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LoginPresenter : BasePresenter<LoginMvpView>() {

    fun login(context: Context, email: String, password: String) {
        ProgressDialogUtils.on().showProgressDialog(context)
        compositeDisposable.add(
            BaseRepository.on().login(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val response = it.body()

                    if (response == null) {
                        mvpView?.onError(DataUtils.getString(R.string.login_error_login_failed))
                    } else {
                        if (response.isSuccessful) {
                            if (response.data.asJsonObject.has(Constants.JsonKeys.ACCESS_TOKEN)) {
                                val accessToken =
                                    response.data.asJsonObject.get(Constants.JsonKeys.ACCESS_TOKEN).asString!!
                                SharedPrefUtils.write(
                                    Constants.PreferenceKeys.ACCESS_TOKEN,
                                    accessToken
                                )

                                val accessType =
                                    response.data.asJsonObject.get(Constants.JsonKeys.ACCESS_TYPE).asString!!
                                SharedPrefUtils.write(
                                    Constants.PreferenceKeys.ACCESS_TYPE,
                                    accessType
                                )

                                val isGoogleAuthEnabled =
                                    response.data
                                        .asJsonObject
                                        .get(Constants.JsonKeys.IS_GOOGLE_AUTH_ENABLED)
                                        .asBoolean

                                SharedPrefUtils.write(
                                    Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET,
                                    isGoogleAuthEnabled
                                )

                                if (response.data.asJsonObject.has(Constants.JsonKeys.USER_INFO)) {
                                    val userInfo =
                                        response.data.asJsonObject.get(Constants.JsonKeys.USER_INFO)!!

                                    if (userInfo.asJsonObject.has(Constants.JsonKeys.ID)) {
                                        SharedPrefUtils.write(
                                            Constants.PreferenceKeys.USER_ID,
                                            userInfo.asJsonObject[Constants.JsonKeys.ID]
                                                .asString!!
                                        )
                                    }

                                    var isGoogleAuthEnabledAndOn = false

                                    if (!(userInfo
                                            .asJsonObject
                                            .get(Constants.JsonKeys.USER_SETTINGS)
                                            .asJsonObject[Constants.JsonKeys.IS_GOOGLE_AUTH_ENABLED_AND_ON]
                                            .isJsonNull)
                                    ) {

                                        val twoFactorAuthStatus =
                                            userInfo
                                                .asJsonObject
                                                .get(Constants.JsonKeys.USER_SETTINGS)
                                                .asJsonObject[Constants.JsonKeys.IS_GOOGLE_AUTH_ENABLED_AND_ON]
                                                .asInt

                                        isGoogleAuthEnabledAndOn = (twoFactorAuthStatus == 1)
                                    }

                                    SharedPrefUtils.write(
                                        Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON,
                                        isGoogleAuthEnabledAndOn
                                    )

                                    if (userInfo.asJsonObject.has(Constants.JsonKeys.FIRST_NAME)) {
                                        val firstName =
                                            userInfo.asJsonObject.get(Constants.JsonKeys.FIRST_NAME).asString!!
                                        SharedPrefUtils.write(
                                            Constants.PreferenceKeys.FIRST_NAME,
                                            firstName
                                        )
                                    }

                                    if (userInfo.asJsonObject.has(Constants.JsonKeys.LAST_NAME)) {
                                        val lastName =
                                            userInfo.asJsonObject.get(Constants.JsonKeys.LAST_NAME).asString!!
                                        SharedPrefUtils.write(
                                            Constants.PreferenceKeys.LAST_NAME,
                                            lastName
                                        )
                                    }

                                    if (userInfo.asJsonObject.has(Constants.JsonKeys.EMAIL)) {
                                        val userEmail =
                                            userInfo.asJsonObject.get(Constants.JsonKeys.EMAIL).asString!!
                                        SharedPrefUtils.write(
                                            Constants.PreferenceKeys.EMAIL,
                                            userEmail
                                        )
                                    }

                                    if (userInfo.asJsonObject.has(Constants.JsonKeys.PHONE)
                                        && !(userInfo.asJsonObject.get(Constants.JsonKeys.PHONE).isJsonNull)
                                    ) {
                                        val phoneNumber =
                                            userInfo.asJsonObject.get(Constants.JsonKeys.PHONE).asString!!
                                        SharedPrefUtils.write(
                                            Constants.PreferenceKeys.PHONE,
                                            phoneNumber
                                        )
                                    }

                                    SharedPrefUtils.write(Constants.PreferenceKeys.LOGGED_IN, true)
                                    mvpView?.onSuccess(response.message)
                                } else {
                                    mvpView?.onError(DataUtils.getString(R.string.login_error_login_failed))
                                }
                            } else {
                                SharedPrefUtils.delete(Constants.PreferenceKeys.ACCESS_TOKEN)
                                mvpView?.onError(DataUtils.getString(R.string.login_error_login_failed))
                            }
                        } else {
                            mvpView?.onError(response.message)
                        }
                    }
                }, {
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.login_error_login_failed))
                    }
                })
        )
    }
}