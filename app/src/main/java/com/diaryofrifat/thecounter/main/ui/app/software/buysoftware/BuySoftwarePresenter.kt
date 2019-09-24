package com.diaryofrifat.thecounter.main.ui.app.software.buysoftware

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.BaseRepository
import com.diaryofrifat.thecounter.main.data.remote.model.Bank
import com.diaryofrifat.thecounter.main.data.remote.model.FeesAndVat
import com.diaryofrifat.thecounter.main.ui.base.component.BasePresenter
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.helper.network.NoConnectivityException
import com.diaryofrifat.thecounter.utils.libs.GsonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class BuySoftwarePresenter : BasePresenter<BuySoftwareMvpView>() {
    fun getSoftwareLicenseDetails(context: Context, softwareID: String) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().getSoftwareLicenseDetails(softwareID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    dialog?.dismiss()
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response != null) {
                            if (response.isSuccessful) {
                                val bankList = GsonUtils.fromJson<List<Bank>>(
                                    response.data
                                        .asJsonObject[Constants.JsonKeys.BANKS]
                                        .asJsonArray
                                        .toString()
                                )

                                val feesAndVat = GsonUtils.fromJson<FeesAndVat>(
                                    response.data
                                        .asJsonObject[Constants.JsonKeys.FEES_AND_VAT]
                                        .asJsonObject
                                        .toString()
                                )

                                val referenceID =
                                    response.data
                                        .asJsonObject[Constants.JsonKeys.REFERENCE_ID]
                                        .asString

                                if (bankList != null && feesAndVat != null) {
                                    mvpView?.onGettingSoftwareLicenseDetails(
                                        referenceID,
                                        bankList,
                                        feesAndVat
                                    )
                                } else {
                                    mvpView?.onError(DataUtils.getString(R.string.error_could_not_load_data))
                                }
                            } else {
                                mvpView?.onError(response.message)
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

    fun requestBankSubscription(
        context: Context, softwareID: String,
        referenceID: String, paymentMethod: String,
        price: String, interval: String, bankID: String
    ) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().requestBankSubscription(
                softwareID, referenceID, paymentMethod,
                price, interval, bankID
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    dialog?.dismiss()
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response != null) {
                            if (response.isSuccessful) {
                                mvpView?.onPlacingOrder(response.message)
                            } else {
                                mvpView?.onError(response.message)
                            }
                        } else {
                            mvpView?.onError(
                                DataUtils
                                    .getString(R.string.buy_software_error_could_not_place_order)
                            )
                        }
                    }
                }, {
                    dialog?.dismiss()
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(
                            DataUtils
                                .getString(R.string.buy_software_error_could_not_place_order)
                        )
                    }
                })
        )
    }
}