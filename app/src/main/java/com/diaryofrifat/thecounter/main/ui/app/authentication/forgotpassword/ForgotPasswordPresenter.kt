package com.diaryofrifat.thecounter.main.ui.app.authentication.forgotpassword

import android.content.Context
import android.text.TextUtils
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.BaseRepository
import com.diaryofrifat.thecounter.main.ui.base.component.BasePresenter
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.helper.network.NoConnectivityException
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