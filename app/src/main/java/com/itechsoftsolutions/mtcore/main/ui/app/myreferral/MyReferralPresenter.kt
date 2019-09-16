package com.itechsoftsolutions.mtcore.main.ui.app.myreferral

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.main.ui.base.getString
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.SharedPrefUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection


class MyReferralPresenter : BasePresenter<MyReferralMvpView>() {
    fun getReferralLink(context: Context) {
        ProgressDialogUtils.on().showProgressDialog(context)

        if (SharedPrefUtils.contains(Constants.PreferenceKeys.REFERRAL_LINK)) {
            mvpView?.onSuccess(SharedPrefUtils.readString(Constants.PreferenceKeys.REFERRAL_LINK))
        } else {
            compositeDisposable.add(
                BaseRepository.on().getDashboardData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                        } else {
                            val response = it.body()

                            if (response == null) {
                                mvpView?.onError(DataUtils.getString(R.string.my_referral_error_could_not_get_url))
                            } else {
                                if (response.isSuccessful
                                    && response.data.asJsonObject.has(Constants.JsonKeys.REFERRAL_LINK)
                                    && !(response.data.asJsonObject.get(Constants.JsonKeys.REFERRAL_LINK).isJsonNull)
                                ) {
                                    SharedPrefUtils.write(
                                        Constants.PreferenceKeys.REFERRAL_LINK,
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.REFERRAL_LINK).asString
                                    )
                                    mvpView?.onSuccess(SharedPrefUtils.readString(Constants.PreferenceKeys.REFERRAL_LINK))
                                } else {
                                    mvpView?.onError(response.message)
                                }
                            }
                        }
                    }, {
                        Timber.d(it)

                        if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                            mvpView?.onError(it.message)
                        } else {
                            mvpView?.onError(DataUtils.getString(R.string.my_referral_error_could_not_get_url))
                        }

                    })
            )
        }
    }

    fun copyTextToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        clipboard?.primaryClip = ClipData.newPlainText(Constants.PreferenceKeys.REFERRAL_LINK, text)
        ToastUtils.nativeShort(getString(R.string.copied_to_clipboard))
    }
}