package com.itechsoftsolutions.mtcore.main.ui.app.authentication.forgotpassword

import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ForgotPasswordPresenter : BasePresenter<ForgotPasswordMvpView>() {
    fun resetPassword(context: Context, email: String) {
        ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().requestToResetPassword(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val baseResponse = it.body()

                    if (baseResponse == null) {
                        mvpView?.onError(DataUtils.getString(R.string.forgot_password_error_of_process))
                    } else {
                        if (baseResponse.isSuccessful
                            && baseResponse.data.asJsonObject.has(Constants.JsonKeys.TOKEN)
                            && !(baseResponse.data.asJsonObject.get(Constants.JsonKeys.TOKEN).isJsonNull)
                        ) {
                            mvpView?.onSuccess(
                                baseResponse.message,
                                baseResponse.data.asJsonObject.get(Constants.JsonKeys.TOKEN).asString
                            )
                        } else {
                            mvpView?.onError(baseResponse.message)
                        }
                    }
                }, {
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.forgot_password_error_of_process))
                    }
                })
        )
    }
}