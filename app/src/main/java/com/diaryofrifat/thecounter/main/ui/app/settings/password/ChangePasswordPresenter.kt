package com.diaryofrifat.thecounter.main.ui.app.settings.password

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.BaseRepository
import com.diaryofrifat.thecounter.main.ui.base.component.BasePresenter
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class ChangePasswordPresenter : BasePresenter<ChangePasswordMvpView>() {
    fun changePassword(
        context: Context,
        oldPassword: String,
        newPassword: String,
        confirmNewPassword: String
    ) {
        ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().changePassword(oldPassword, newPassword, confirmNewPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val baseResponse = it.body()

                        if (baseResponse == null) {
                            mvpView?.onError(DataUtils.getString(R.string.change_password_error_could_not_change))
                        } else {
                            if (baseResponse.isSuccessful) {
                                mvpView?.onSuccess(baseResponse.message)
                            } else {
                                mvpView?.onError(baseResponse.message)
                            }
                        }
                    }
                }, {
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.change_password_error_could_not_change))
                    }
                })
        )
    }
}