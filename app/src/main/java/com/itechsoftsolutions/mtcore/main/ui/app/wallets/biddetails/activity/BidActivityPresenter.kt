package com.itechsoftsolutions.mtcore.main.ui.app.wallets.biddetails.activity

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.data.remote.model.BidActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class BidActivityPresenter : BasePresenter<BidActivityMvpView>() {
    var walletBalance: String? = null

    fun getBidsHistory(context: Context, page: Int) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().getBidsHistory(page)
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
                                val bidsHistoryObject =
                                    response.data.asJsonObject[Constants.JsonKeys.BIDS_HISTORY]

                                val currentPage =
                                    bidsHistoryObject
                                        .asJsonObject[Constants.JsonKeys.CURRENT_PAGE]
                                        .asInt

                                val lastPage =
                                    bidsHistoryObject
                                        .asJsonObject[Constants.JsonKeys.LAST_PAGE]
                                        .asInt

                                val nextPageUrl: String? =
                                    if (bidsHistoryObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .isJsonNull
                                    ) {
                                        null
                                    } else {
                                        bidsHistoryObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .asString
                                    }

                                val list: MutableList<BidActivity> = ArrayList()

                                bidsHistoryObject
                                    .asJsonObject[Constants.JsonKeys.DATA]
                                    .asJsonArray
                                    .forEach { element ->
                                        list.add(
                                            BidActivity(
                                                element.asJsonObject[Constants.JsonKeys.BIDSTORE_ORDER_ID].asString,
                                                element.asJsonObject[Constants.JsonKeys.CREATED_AT].asString,
                                                element.asJsonObject[Constants.JsonKeys.AMOUNT].asString,
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