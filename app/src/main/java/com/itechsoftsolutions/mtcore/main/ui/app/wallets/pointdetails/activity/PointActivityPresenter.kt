package com.itechsoftsolutions.mtcore.main.ui.app.wallets.pointdetails.activity

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.data.remote.model.PointActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class PointActivityPresenter : BasePresenter<PointActivityMvpView>() {
    var walletBalance: String? = null

    fun getPointHistory(context: Context, page: Int) {
        compositeDisposable.add(
            BaseRepository.on().getPointsHistory(page)
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
                                    response.data.asJsonObject[Constants.JsonKeys.POINT_WITHDRAWAL_HISTORY]

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

                                val list: MutableList<PointActivity> = ArrayList()

                                historyObject
                                    .asJsonObject[Constants.JsonKeys.DATA]
                                    .asJsonArray
                                    .forEach { element ->
                                        list.add(
                                            PointActivity(
                                                element.asJsonObject[Constants.JsonKeys.POINT_WITHDRAWAL_ID].asString,
                                                element.asJsonObject[Constants.JsonKeys.POINTS].asString,
                                                element.asJsonObject[Constants.JsonKeys.PRICE].asString,
                                                element.asJsonObject[Constants.JsonKeys.USER_BANK_NAME].asString,
                                                if (element.asJsonObject[Constants.JsonKeys.ADMIN_MESSAGE].isJsonNull) {
                                                    null
                                                } else {
                                                    element.asJsonObject[Constants.JsonKeys.ADMIN_MESSAGE].asString
                                                },
                                                element.asJsonObject[Constants.JsonKeys.STATUS].asString,
                                                if (element.asJsonObject[Constants.JsonKeys.BANK_RECEIPT].isJsonNull) {
                                                    null
                                                } else {
                                                    element.asJsonObject[Constants.JsonKeys.BANK_RECEIPT].asString
                                                },
                                                element.asJsonObject[Constants.JsonKeys.BANK_ACCOUNT_ID].asString,
                                                currentPage,
                                                lastPage,
                                                nextPageUrl
                                            )
                                        )
                                    }

                                mvpView?.onSuccess(list)
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

    fun confirmPointWithdrawalHistory(context: Context, item: PointActivity) {

        var toBeChangedItem = item

        compositeDisposable.add(
            BaseRepository.on().confirmPointWithdrawalHistory(toBeChangedItem.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response == null) {
                            mvpView?.onError(DataUtils.getString(R.string.point_activity_error_could_not_confirm))
                        } else {
                            if (response.isSuccessful) {
                                toBeChangedItem.status = Constants.Common.STATUS_CONFIRM
                                mvpView?.onConfirmation(response.message, toBeChangedItem)
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
                        mvpView?.onError(DataUtils.getString(R.string.point_activity_error_could_not_confirm))
                    }

                })
        )
    }
}