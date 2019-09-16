package com.itechsoftsolutions.mtcore.main.ui.app.software.history

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.data.remote.model.SoftwareLicensePurchaseHistory
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.toTitleCase
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class SoftwareLicenceHistoryPresenter : BasePresenter<SoftwareLicenceHistoryMvpView>() {
    fun getSoftwareListFromDatabase() {
        compositeDisposable.add(
            BaseRepository.on().getSoftwareListFromDatabase()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    mvpView?.onGettingSoftwares(it)
                }, {
                    Timber.d(it)
                })
        )
    }

    fun getSoftwareListFromCloud(context: Context) {
        compositeDisposable.add(
            BaseRepository.on().getSoftwareListFromCloud()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response != null && response.isSuccessful) {
                            compositeDisposable.add(
                                BaseRepository.on().insertSoftwaresToDatabase(
                                    response.data.collection
                                ).observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe({

                                    }, { e ->
                                        Timber.d(e)
                                    })
                            )
                        }
                    }
                }, {
                    Timber.d(it)
                })
        )
    }

    fun getSoftwareLicensePurchaseHistory(context: Context, page: Int) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().getSoftwareLicensePurchaseHistory(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    dialog?.dismiss()
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response == null) {
                            mvpView?.onError(DataUtils.getString(R.string.error_could_not_load_data))
                        } else {
                            if (response.isSuccessful) {
                                val historyObject =
                                    response.data.asJsonObject[Constants.JsonKeys.HISTORIES]

                                val currentPage =
                                    historyObject.asJsonObject[Constants.JsonKeys.CURRENT_PAGE].asInt

                                val lastPage =
                                    historyObject.asJsonObject[Constants.JsonKeys.LAST_PAGE].asInt

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

                                val list: MutableList<SoftwareLicensePurchaseHistory> = ArrayList()

                                historyObject
                                    .asJsonObject[Constants.JsonKeys.DATA]
                                    .asJsonArray
                                    .forEach { element ->
                                        list.add(
                                            SoftwareLicensePurchaseHistory(
                                                element.asJsonObject[Constants.JsonKeys.ORDER_ID].asString,
                                                element.asJsonObject[Constants.JsonKeys.SOFTWARE_ID].asString,
                                                element.asJsonObject[Constants.JsonKeys.PRICE].asLong,
                                                element.asJsonObject[Constants.JsonKeys.STATUS]
                                                    .asString
                                                    .replace("_", Constants.Default.SPACE_STRING)
                                                    .toTitleCase(),
                                                element.asJsonObject[Constants.JsonKeys.UPDATED_AT].asString,
                                                currentPage,
                                                lastPage,
                                                nextPageUrl
                                            )
                                        )
                                    }

                                mvpView?.onGettingSoftwareLicensePurchaseHistory(list)
                            } else {
                                mvpView?.onError(response.message)
                            }
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