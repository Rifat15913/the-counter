package com.diaryofrifat.thecounter.main.ui.app.wallets.mtcoredetails.receive

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.BaseRepository
import com.diaryofrifat.thecounter.main.ui.base.component.BasePresenter
import com.diaryofrifat.thecounter.main.ui.base.getString
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.helper.network.NoConnectivityException
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class ReceiveMtcorePresenter : BasePresenter<ReceiveMtcoreMvpView>() {

    var walletID: String? = null
    var walletAddress: String? = null

    fun getMtcoreWalletReceivingAddress(context: Context, walletID: String) {
        compositeDisposable.add(
            BaseRepository.on().getMtcoreWalletReceivingAddress(walletID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response != null && response.isSuccessful) {

                            if (response.data.asJsonObject.has(Constants.JsonKeys.ADDRESS)
                                && !(response.data.asJsonObject.get(Constants.JsonKeys.ADDRESS).isJsonNull)
                            ) {
                                val addressObject =
                                    response.data.asJsonObject.get(Constants.JsonKeys.ADDRESS)

                                if (addressObject.asJsonObject.has(Constants.JsonKeys.ADDRESS)
                                    && !(addressObject.asJsonObject.get(Constants.JsonKeys.ADDRESS).isJsonNull)
                                ) {
                                    walletAddress = addressObject
                                        .asJsonObject
                                        .get(Constants.JsonKeys.ADDRESS)
                                        .asString

                                    mvpView?.onSuccess(walletAddress!!)
                                }
                            }
                        }
                    }
                }, {
                    Timber.d(it)
                })
        )
    }

    fun copyTextToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        clipboard?.primaryClip = ClipData.newPlainText(Constants.Default.DEFAULT_STRING, text)
        ToastUtils.nativeShort(getString(R.string.copied_to_clipboard))
    }

    fun generateMtcoreWalletReceivingAddress(context: Context, walletID: String) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().generateMtcoreWalletReceivingAddress(walletID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    dialog?.dismiss()
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response != null && response.isSuccessful) {
                            if (response.data.asJsonObject.has(Constants.JsonKeys.ADDRESS)
                                && !(response.data.asJsonObject.get(Constants.JsonKeys.ADDRESS).isJsonNull)
                            ) {
                                walletAddress = response.data
                                    .asJsonObject
                                    .get(Constants.JsonKeys.ADDRESS)
                                    .asString

                                mvpView?.onSuccess(walletAddress!!)
                            } else {
                                mvpView?.onError(DataUtils.getString(R.string.error_could_not_load_data))
                            }
                        } else {
                            mvpView?.onError(DataUtils.getString(R.string.error_could_not_load_data))
                        }
                    }
                }, {
                    dialog?.dismiss()
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.error_could_not_load_data))
                    }
                })
        )
    }
}