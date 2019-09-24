package com.diaryofrifat.thecounter.main.ui.app.wallets.mtcoredetails.activity.tab

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.BaseRepository
import com.diaryofrifat.thecounter.main.data.remote.model.MtcoreWalletActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BasePresenter
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class ActivityMtcoreTabPresenter : BasePresenter<ActivityMtcoreTabMvpView>() {

    fun getMtcoreDepositHistory(context: Context, walletID: String, page: Int) {
        compositeDisposable.add(
            BaseRepository.on().getMtcoreDepositHistory(walletID, page)
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
                                val historyObject =
                                    response.data.asJsonObject[Constants.JsonKeys.DEPOSIT]

                                val currentPage =
                                    historyObject
                                        .asJsonObject[Constants.JsonKeys.CURRENT_PAGE]
                                        .asInt

                                val lastPage =
                                    historyObject
                                        .asJsonObject[Constants.JsonKeys.LAST_PAGE]
                                        .asInt

                                val nextPageUrl: String? =
                                    if (historyObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .isJsonNull
                                    ) {
                                        null
                                    } else {
                                        historyObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .asString
                                    }

                                val list: MutableList<MtcoreWalletActivity> = ArrayList()

                                historyObject
                                    .asJsonObject[Constants.JsonKeys.DATA]
                                    .asJsonArray
                                    .forEach { element ->
                                        list.add(
                                            MtcoreWalletActivity(
                                                element.asJsonObject[Constants.JsonKeys.TRANSACTION_ID].asString,
                                                element.asJsonObject[Constants.JsonKeys.ADDRESS_TYPE].asInt,
                                                element.asJsonObject[Constants.JsonKeys.ADDRESS].asString,
                                                element.asJsonObject[Constants.JsonKeys.AMOUNT].asString,
                                                element.asJsonObject[Constants.JsonKeys.FEES].asString,
                                                element.asJsonObject[Constants.JsonKeys.STATUS].asInt,
                                                element.asJsonObject[Constants.JsonKeys.CONFIRMATIONS].asInt,
                                                element.asJsonObject[Constants.JsonKeys.CREATED_AT].asString,
                                                currentPage,
                                                lastPage,
                                                nextPageUrl
                                            )
                                        )
                                    }

                                mvpView?.onGettingHistory(list)
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

    fun getMtcoreWithdrawalHistory(context: Context, walletID: String, page: Int) {
        compositeDisposable.add(
            BaseRepository.on().getMtcoreWithdrawalHistory(walletID, page)
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
                                val historyObject =
                                    response.data.asJsonObject[Constants.JsonKeys.WITHDRAWALS]

                                val currentPage =
                                    historyObject
                                        .asJsonObject[Constants.JsonKeys.CURRENT_PAGE]
                                        .asInt

                                val lastPage =
                                    historyObject
                                        .asJsonObject[Constants.JsonKeys.LAST_PAGE]
                                        .asInt

                                val nextPageUrl: String? =
                                    if (historyObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .isJsonNull
                                    ) {
                                        null
                                    } else {
                                        historyObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .asString
                                    }

                                val list: MutableList<MtcoreWalletActivity> = ArrayList()

                                historyObject
                                    .asJsonObject[Constants.JsonKeys.DATA]
                                    .asJsonArray
                                    .forEach { element ->
                                        list.add(
                                            MtcoreWalletActivity(
                                                element.asJsonObject[Constants.JsonKeys.TRANSACTION_ID].asString,
                                                element.asJsonObject[Constants.JsonKeys.ADDRESS_TYPE].asInt,
                                                element.asJsonObject[Constants.JsonKeys.ADDRESS].asString,
                                                element.asJsonObject[Constants.JsonKeys.AMOUNT].asString,
                                                element.asJsonObject[Constants.JsonKeys.FEES].asString,
                                                element.asJsonObject[Constants.JsonKeys.STATUS].asInt,
                                                element.asJsonObject[Constants.JsonKeys.CONFIRMATIONS].asInt,
                                                element.asJsonObject[Constants.JsonKeys.CREATED_AT].asString,
                                                currentPage,
                                                lastPage,
                                                nextPageUrl
                                            )
                                        )
                                    }

                                mvpView?.onGettingHistory(list)
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