package com.itechsoftsolutions.mtcore.main.ui.app.ranking.rankinglist

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.data.remote.model.Ranker
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.main.ui.base.getString
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection
import java.util.*
import kotlin.collections.ArrayList

class RankingListPresenter : BasePresenter<RankingListMvpView>() {
    val topSellerRankerList: MutableList<Ranker> = ArrayList()
    val topMonthlyConsumerRankerList: MutableList<Ranker> = ArrayList()
    val topYearlyConsumerRankerList: MutableList<Ranker> = ArrayList()
    val topMonthlyBideratorRankerList: MutableList<Ranker> = ArrayList()
    val topYearlyBideratorRankerList: MutableList<Ranker> = ArrayList()

    fun getRankingList(context: Context) {
        topSellerRankerList.clear()
        topMonthlyBideratorRankerList.clear()
        topMonthlyBideratorRankerList.clear()
        topYearlyConsumerRankerList.clear()
        topYearlyBideratorRankerList.clear()

        ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().getRankingList()
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
                                if (response.data.asJsonObject.has(Constants.JsonKeys.TOP_SELLERS)
                                    && response.data.asJsonObject.get(Constants.JsonKeys.TOP_SELLERS).isJsonArray
                                ) {
                                    val topSellerArray =
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.TOP_SELLERS).asJsonArray

                                    for (i in 0 until topSellerArray.size()) {
                                        val element = topSellerArray.get(i)
                                        topSellerRankerList.add(
                                            Ranker(
                                                String.format(
                                                    Locale.getDefault(),
                                                    getString(R.string.ranking_list_name),
                                                    element.asJsonObject[Constants.JsonKeys.FIRST_NAME].asString,
                                                    element.asJsonObject[Constants.JsonKeys.LAST_NAME].asString
                                                ),
                                                String.format(
                                                    Locale.getDefault(),
                                                    getString(R.string.ranking_list_points),
                                                    element.asJsonObject[Constants.JsonKeys.MONTHLY_SELLER_POINTS]
                                                        .asLong.toString()
                                                ),
                                                (i + 1)
                                            )
                                        )
                                    }

                                    val topMonthlyConsumerArray =
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.TOP_CONSUMERS)
                                            .asJsonObject
                                            .get(Constants.JsonKeys.MONTHLY)
                                            .asJsonArray

                                    for (i in 0 until topMonthlyConsumerArray.size()) {
                                        val element = topMonthlyConsumerArray.get(i)
                                        topMonthlyConsumerRankerList.add(
                                            Ranker(
                                                String.format(
                                                    Locale.getDefault(),
                                                    getString(R.string.ranking_list_name),
                                                    element.asJsonObject[Constants.JsonKeys.FIRST_NAME].asString,
                                                    element.asJsonObject[Constants.JsonKeys.LAST_NAME].asString
                                                ),
                                                String.format(
                                                    Locale.getDefault(),
                                                    getString(R.string.ranking_list_points),
                                                    element.asJsonObject[Constants.JsonKeys.MONTHLY_CONSUMER_POINTS]
                                                        .asLong.toString()
                                                ),
                                                (i + 1)
                                            )
                                        )
                                    }

                                    val topYearlyConsumerArray =
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.TOP_CONSUMERS)
                                            .asJsonObject
                                            .get(Constants.JsonKeys.YEARLY)
                                            .asJsonArray

                                    for (i in 0 until topYearlyConsumerArray.size()) {
                                        val element = topYearlyConsumerArray.get(i)
                                        topYearlyConsumerRankerList.add(
                                            Ranker(
                                                String.format(
                                                    Locale.getDefault(),
                                                    getString(R.string.ranking_list_name),
                                                    element.asJsonObject[Constants.JsonKeys.FIRST_NAME].asString,
                                                    element.asJsonObject[Constants.JsonKeys.LAST_NAME].asString
                                                ),
                                                String.format(
                                                    Locale.getDefault(),
                                                    getString(R.string.ranking_list_points),
                                                    element.asJsonObject[Constants.JsonKeys.YEARLY_CONSUMER_POINTS]
                                                        .asLong.toString()
                                                ),
                                                (i + 1)
                                            )
                                        )
                                    }

                                    val topMonthlyBideratorArray =
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.TOP_BIDERATORS)
                                            .asJsonObject
                                            .get(Constants.JsonKeys.MONTHLY)
                                            .asJsonArray

                                    for (i in 0 until topMonthlyBideratorArray.size()) {
                                        val element = topMonthlyBideratorArray.get(i)
                                        topMonthlyBideratorRankerList.add(
                                            Ranker(
                                                String.format(
                                                    Locale.getDefault(),
                                                    getString(R.string.ranking_list_name),
                                                    element.asJsonObject[Constants.JsonKeys.FIRST_NAME].asString,
                                                    element.asJsonObject[Constants.JsonKeys.LAST_NAME].asString
                                                ),
                                                String.format(
                                                    Locale.getDefault(),
                                                    getString(R.string.ranking_list_points),
                                                    element.asJsonObject[Constants.JsonKeys.MONTHLY_BIDERATOR_POINTS]
                                                        .asLong.toString()
                                                ),
                                                (i + 1)
                                            )
                                        )
                                    }

                                    val topYearlyBideratorArray =
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.TOP_BIDERATORS)
                                            .asJsonObject
                                            .get(Constants.JsonKeys.YEARLY)
                                            .asJsonArray

                                    for (i in 0 until topYearlyBideratorArray.size()) {
                                        val element = topYearlyBideratorArray.get(i)
                                        topYearlyBideratorRankerList.add(
                                            Ranker(
                                                String.format(
                                                    Locale.getDefault(),
                                                    getString(R.string.ranking_list_name),
                                                    element.asJsonObject[Constants.JsonKeys.FIRST_NAME].asString,
                                                    element.asJsonObject[Constants.JsonKeys.LAST_NAME].asString
                                                ),
                                                String.format(
                                                    Locale.getDefault(),
                                                    getString(R.string.ranking_list_points),
                                                    element.asJsonObject[Constants.JsonKeys.YEARLY_BIDERATOR_POINTS]
                                                        .asLong.toString()
                                                ),
                                                (i + 1)
                                            )
                                        )
                                    }

                                    mvpView?.onGettingData(topSellerRankerList)
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

    fun updateRankerList(sessionPosition: Int, actorTypePosition: Int) {
        when (actorTypePosition) {
            0 -> {
                mvpView?.onGettingData(topSellerRankerList)
            }

            1 -> {
                mvpView?.onGettingData(
                    if (sessionPosition == 0) {
                        topMonthlyConsumerRankerList
                    } else {
                        topYearlyConsumerRankerList
                    }
                )
            }

            2 -> {
                mvpView?.onGettingData(
                    if (sessionPosition == 0) {
                        topMonthlyBideratorRankerList
                    } else {
                        topYearlyBideratorRankerList
                    }
                )
            }
        }
    }
}