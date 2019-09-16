package com.itechsoftsolutions.mtcore.main.ui.app.authentication.verification

import android.app.Activity
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
import java.net.HttpURLConnection

class CodeVerificationPresenter : BasePresenter<CodeVerificationMvpView>() {
    fun removeTwoFactorAuthentication(context: Context, googleAuthCode: String) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().removeTwoFactorAuthentication(googleAuthCode)
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
                            mvpView?.onError(DataUtils.getString(R.string.settings_error_remove_2_factor_authentication))
                        }
                    }
                }, {
                    dialog?.dismiss()
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.settings_error_remove_2_factor_authentication))
                    }
                })
        )
    }

    fun verifyTwoFactorAuthentication(context: Context, googleAuthCode: String) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().verifyTwoFactorAuthentication(googleAuthCode)
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
                                SharedPrefUtils
                                    .write(
                                        Constants.PreferenceKeys.IS_GOOGLE_AUTH_VERIFIED,
                                        true
                                    )
                                mvpView?.onSuccess(response.message)
                            } else {
                                mvpView?.onError(response.message)
                            }
                        } else {
                            mvpView?.onError(DataUtils.getString(R.string.settings_error_could_not_verify))
                        }
                    }
                }, {
                    dialog?.dismiss()
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.settings_error_could_not_verify))
                    }
                })
        )
    }

    fun sendMtcore(
        context: Context,
        googleAuthCode: String,
        walletAddress: String,
        walletID: String,
        amount: String,
        note: String?
    ) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().sendMtcore(googleAuthCode, walletAddress, walletID, amount, note)
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
                            mvpView?.onError(DataUtils.getString(R.string.error_something_went_wrong))
                        }
                    }
                }, {
                    dialog?.dismiss()
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.error_something_went_wrong))
                    }
                })
        )
    }
}