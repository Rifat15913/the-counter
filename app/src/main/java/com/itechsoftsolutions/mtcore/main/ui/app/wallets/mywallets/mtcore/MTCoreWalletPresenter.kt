package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets.mtcore

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class MTCoreWalletPresenter : BasePresenter<MTCoreWalletMvpView>() {

    var walletID: String? = null

    fun getWalletBalance(context: Context) {
        compositeDisposable.add(
            BaseRepository.on().getWallets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response == null) {
                            mvpView?.onError(DataUtils.getString(R.string.error_could_not_load_data))
                        } else {
                            if (response.isSuccessful) {
                                if (response.data.asJsonObject.has(Constants.JsonKeys.MTR_WALLET)
                                    && !(response.data.asJsonObject.get(Constants.JsonKeys.MTR_WALLET).isJsonNull)
                                ) {
                                    val walletObject =
                                        response.data.asJsonObject.get(Constants.JsonKeys.MTR_WALLET)

                                    if (walletObject.asJsonObject.has(Constants.JsonKeys.ID)
                                        && !(walletObject.asJsonObject.get(Constants.JsonKeys.ID).isJsonNull)
                                    ) {
                                        walletID = walletObject
                                            .asJsonObject
                                            .get(Constants.JsonKeys.ID)
                                            .asString
                                    } else {
                                        mvpView?.onError(DataUtils.getString(R.string.error_could_not_load_data))
                                    }

                                    if (walletObject.asJsonObject.has(Constants.JsonKeys.BALANCE)
                                        && !(walletObject.asJsonObject.get(Constants.JsonKeys.BALANCE).isJsonNull)
                                    ) {
                                        mvpView?.onSuccess(
                                            walletObject
                                                .asJsonObject
                                                .get(Constants.JsonKeys.BALANCE)
                                                .asString
                                        )
                                    } else {
                                        mvpView?.onError(DataUtils.getString(R.string.error_could_not_load_data))
                                    }
                                } else {
                                    mvpView?.onError(DataUtils.getString(R.string.error_could_not_load_data))
                                }
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
                        mvpView?.onError(DataUtils.getString(R.string.error_could_not_load_data))
                    }

                })
        )
    }
}