package com.diaryofrifat.thecounter.main.ui.app.wallets.mywallets.point

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.BaseRepository
import com.diaryofrifat.thecounter.main.data.remote.model.BankAccountSummary
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

class PointWalletPresenter : BasePresenter<PointWalletMvpView>() {

    var mBankAccountList: List<BankAccountSummary> = ArrayList()

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
                                if (response.data.asJsonObject.has(Constants.JsonKeys.POINT_WALLET)
                                    && !(response.data.asJsonObject.get(Constants.JsonKeys.POINT_WALLET).isJsonNull)
                                ) {
                                    val walletObject =
                                        response.data.asJsonObject.get(Constants.JsonKeys.POINT_WALLET)

                                    if (walletObject.asJsonObject.has(Constants.JsonKeys.POINTS)
                                        && !(walletObject.asJsonObject.get(Constants.JsonKeys.POINTS).isJsonNull)
                                    ) {
                                        mvpView?.onSuccess(
                                            walletObject
                                                .asJsonObject
                                                .get(Constants.JsonKeys.POINTS)
                                                .asString
                                        )
                                    } else {
                                        mvpView?.onError(DataUtils.getString(R.string.error_could_not_load_data))
                                    }

                                    if (response.data.asJsonObject.get(Constants.JsonKeys.BANK_ACCOUNTS).isJsonObject) {
                                        val bankAccountObject =
                                            response.data.asJsonObject.get(Constants.JsonKeys.BANK_ACCOUNTS)

                                        val list = GsonUtils.fromJson<List<BankAccountSummary>>(
                                            bankAccountObject.asJsonObject[Constants.JsonKeys.DATA]
                                                .asJsonArray
                                                .toString()
                                        )

                                        if (list != null && list.isNotEmpty()) {
                                            mBankAccountList = list
                                        }
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

    fun requestPointWithdrawal(context: Context, bankAccountID: String, amount: String) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().requestPointWithdrawal(bankAccountID, amount)
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
                                mvpView?.onSuccessfulWithdrawalRequest(response.message)
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
                            DataUtils.getString(R.string.buy_software_error_could_not_place_order)
                        )
                    }
                })
        )
    }
}