package com.diaryofrifat.thecounter.main.ui.app.ranking.myranking

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.BaseRepository
import com.diaryofrifat.thecounter.main.data.remote.model.MyRanking
import com.diaryofrifat.thecounter.main.ui.base.component.BasePresenter
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class MyRankingPresenter : BasePresenter<MyRankingMvpView>() {
    fun getMyRankings(context: Context) {
        compositeDisposable.add(
            BaseRepository.on().getMyRankings()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response == null) {
                            mvpView?.onError(DataUtils.getString(R.string.my_rankings_error_could_not_load_data))
                        } else {
                            if (response.isSuccessful) {
                                if (response.data.asJsonObject.has(Constants.JsonKeys.SELLER_POINTS)
                                    && !(response.data.asJsonObject.get(Constants.JsonKeys.SELLER_POINTS).isJsonNull)
                                    && response.data.asJsonObject.has(Constants.JsonKeys.BIDERATOR_POINTS)
                                    && !(response.data.asJsonObject.get(Constants.JsonKeys.BIDERATOR_POINTS).isJsonNull)
                                    && response.data.asJsonObject.has(Constants.JsonKeys.CONSUMER_POINTS)
                                    && !(response.data.asJsonObject.get(Constants.JsonKeys.CONSUMER_POINTS).isJsonNull)
                                ) {
                                    val sellerPointsObject =
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.SELLER_POINTS)

                                    val bideratorPointsObject =
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.BIDERATOR_POINTS)

                                    val consumerPointsObject =
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.CONSUMER_POINTS)


                                    mvpView?.onGettingRankings(
                                        sellerPointsObject.asJsonObject.get(Constants.JsonKeys.MONTHLY).asString,
                                        bideratorPointsObject.asJsonObject.get(Constants.JsonKeys.MONTHLY).asString,
                                        bideratorPointsObject.asJsonObject.get(Constants.JsonKeys.YEARLY).asString,
                                        consumerPointsObject.asJsonObject.get(Constants.JsonKeys.MONTHLY).asString,
                                        consumerPointsObject.asJsonObject.get(Constants.JsonKeys.YEARLY).asString
                                    )
                                } else {
                                    mvpView?.onError(DataUtils.getString(R.string.my_rankings_error_could_not_load_data))
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
                        mvpView?.onError(DataUtils.getString(R.string.my_rankings_error_could_not_load_data))
                    }

                })
        )
    }

    fun getMySellerHistory(context: Context, page: Int) {
        ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().getMySellerHistory(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response == null) {
                            mvpView?.onError(DataUtils.getString(R.string.my_rankings_error_could_not_load_data))
                        } else {
                            if (response.isSuccessful) {
                                val sellerHistoryObject =
                                    response.data.asJsonObject[Constants.JsonKeys.SELLER_HISTORY]

                                val currentPage =
                                    sellerHistoryObject.asJsonObject[Constants.JsonKeys.CURRENT_PAGE].asInt

                                val lastPage =
                                    sellerHistoryObject.asJsonObject[Constants.JsonKeys.LAST_PAGE].asInt

                                val nextPageUrl: String? =
                                    if (sellerHistoryObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .isJsonNull
                                    ) {
                                        null
                                    } else {
                                        sellerHistoryObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .asString
                                    }

                                val list: MutableList<MyRanking> = ArrayList()

                                sellerHistoryObject
                                    .asJsonObject[Constants.JsonKeys.DATA]
                                    .asJsonArray
                                    .forEach { element ->
                                        list.add(
                                            MyRanking(
                                                MyRanking.SELLER,
                                                element.asJsonObject[Constants.JsonKeys.POINTS].asLong,
                                                element.asJsonObject[Constants.JsonKeys.SOFTWARE_ID].asString,
                                                null,
                                                element.asJsonObject[Constants.JsonKeys.CREATED_AT].asString,
                                                currentPage,
                                                lastPage,
                                                nextPageUrl
                                            )
                                        )
                                    }

                                mvpView?.onGettingSellerHistory(list)
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
                        mvpView?.onError(DataUtils.getString(R.string.my_rankings_error_could_not_load_data))
                    }

                })
        )
    }

    fun getMyBideratorHistory(context: Context, page: Int) {
        ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().getMyBideratorHistory(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response == null) {
                            mvpView?.onError(DataUtils.getString(R.string.my_rankings_error_could_not_load_data))
                        } else {
                            if (response.isSuccessful) {
                                val bideratorHistoryObject =
                                    response.data.asJsonObject[Constants.JsonKeys.BIDERATOR_HISTORY]

                                val currentPage =
                                    bideratorHistoryObject.asJsonObject[Constants.JsonKeys.CURRENT_PAGE].asInt

                                val lastPage =
                                    bideratorHistoryObject.asJsonObject[Constants.JsonKeys.LAST_PAGE].asInt

                                val nextPageUrl: String? =
                                    if (bideratorHistoryObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .isJsonNull
                                    ) {
                                        null
                                    } else {
                                        bideratorHistoryObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .asString
                                    }

                                val list: MutableList<MyRanking> = ArrayList()

                                bideratorHistoryObject
                                    .asJsonObject[Constants.JsonKeys.DATA]
                                    .asJsonArray
                                    .forEach { element ->
                                        list.add(
                                            MyRanking(
                                                MyRanking.BIDERATOR,
                                                element.asJsonObject[Constants.JsonKeys.POINTS].asLong,
                                                null,
                                                null,
                                                element.asJsonObject[Constants.JsonKeys.CREATED_AT].asString,
                                                currentPage,
                                                lastPage,
                                                nextPageUrl,
                                                element.asJsonObject[Constants.JsonKeys.TITLE].asString
                                            )
                                        )
                                    }

                                mvpView?.onGettingBideratorHistory(list)
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
                        mvpView?.onError(DataUtils.getString(R.string.my_rankings_error_could_not_load_data))
                    }

                })
        )
    }

    fun getMyConsumerHistory(context: Context, page: Int) {
        ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().getMyConsumerHistory(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response == null) {
                            mvpView?.onError(DataUtils.getString(R.string.my_rankings_error_could_not_load_data))
                        } else {
                            if (response.isSuccessful) {
                                val consumerHistoryObject =
                                    response.data.asJsonObject[Constants.JsonKeys.CONSUMER_HISTORY]

                                val currentPage =
                                    consumerHistoryObject.asJsonObject[Constants.JsonKeys.CURRENT_PAGE].asInt

                                val lastPage =
                                    consumerHistoryObject.asJsonObject[Constants.JsonKeys.LAST_PAGE].asInt

                                val nextPageUrl: String? =
                                    if (consumerHistoryObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .isJsonNull
                                    ) {
                                        null
                                    } else {
                                        consumerHistoryObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .asString
                                    }

                                val list: MutableList<MyRanking> = ArrayList()

                                consumerHistoryObject
                                    .asJsonObject[Constants.JsonKeys.DATA]
                                    .asJsonArray
                                    .forEach { element ->
                                        list.add(
                                            MyRanking(
                                                MyRanking.CONSUMER,
                                                element.asJsonObject[Constants.JsonKeys.POINTS].asLong,
                                                element.asJsonObject[Constants.JsonKeys.SOFTWARE_ID].asString,
                                                element.asJsonObject[Constants.JsonKeys.PRODUCT_TYPE].asInt,
                                                element.asJsonObject[Constants.JsonKeys.CREATED_AT].asString,
                                                currentPage,
                                                lastPage,
                                                nextPageUrl
                                            )
                                        )
                                    }

                                mvpView?.onGettingConsumerHistory(list)
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
                        mvpView?.onError(DataUtils.getString(R.string.my_rankings_error_could_not_load_data))
                    }

                })
        )
    }

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
}