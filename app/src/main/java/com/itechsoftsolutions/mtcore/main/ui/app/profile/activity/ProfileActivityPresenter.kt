package com.itechsoftsolutions.mtcore.main.ui.app.profile.activity

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.data.remote.model.ProfileActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class ProfileActivityPresenter : BasePresenter<ProfileActivityMvpView>() {
    fun getProfileActivity(context: Context, page: Int) {
        compositeDisposable.add(
            BaseRepository.on().getProfileActivity(page)
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
                                val activityObject =
                                    response.data.asJsonObject[Constants.JsonKeys.ACTIVITY]

                                val currentPage =
                                    activityObject.asJsonObject[Constants.JsonKeys.CURRENT_PAGE].asInt

                                val lastPage =
                                    activityObject.asJsonObject[Constants.JsonKeys.LAST_PAGE].asInt

                                val nextPageUrl: String? =
                                    if (activityObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .isJsonNull
                                    ) {
                                        null
                                    } else {
                                        activityObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .asString
                                    }

                                val list: MutableList<ProfileActivity> = ArrayList()

                                activityObject
                                    .asJsonObject[Constants.JsonKeys.DATA]
                                    .asJsonArray
                                    .forEach { element ->
                                        list.add(
                                            ProfileActivity(
                                                element.asJsonObject[Constants.JsonKeys.ID].asLong,
                                                element.asJsonObject[Constants.JsonKeys.ACTION].asString,
                                                element.asJsonObject[Constants.JsonKeys.IP_ADDRESS].asString,
                                                element.asJsonObject[Constants.JsonKeys.SOURCE].asString,
                                                element.asJsonObject[Constants.JsonKeys.LOCATION].asString,
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