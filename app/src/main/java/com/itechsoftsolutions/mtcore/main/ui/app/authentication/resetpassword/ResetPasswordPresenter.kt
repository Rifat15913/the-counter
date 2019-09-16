package com.itechsoftsolutions.mtcore.main.ui.app.authentication.resetpassword

import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ResetPasswordPresenter : BasePresenter<ResetPasswordMvpView>() {
    fun resetPassword(context: Context, email: String, token: String, password: String) {
        ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().resetPassword(email, token, password, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val baseResponse = it.body()

                    if (baseResponse == null) {
                        mvpView?.onError(DataUtils.getString(R.string.reset_password_error_could_not_reset))
                    } else {
                        if (baseResponse.isSuccessful) {
                            mvpView?.onSuccess(baseResponse.message)
                        } else {
                            mvpView?.onError(baseResponse.message)
                        }
                    }
                }, {
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.reset_password_error_could_not_reset))
                    }
                })
        )
    }
}