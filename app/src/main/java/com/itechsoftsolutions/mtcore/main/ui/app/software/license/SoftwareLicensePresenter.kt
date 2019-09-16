package com.itechsoftsolutions.mtcore.main.ui.app.software.license

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareStatusEntity
import com.itechsoftsolutions.mtcore.main.data.remote.response.BaseResponse
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import id.zelory.compressor.Compressor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber
import java.io.File
import java.net.HttpURLConnection

class SoftwareLicensePresenter : BasePresenter<SoftwareLicenseMvpView>() {
    fun getSoftwareStatusList(context: Context) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)
        compositeDisposable.add(
            BaseRepository.on().getSoftwareStatusListFromDatabase()
                .zipWith(BaseRepository.on().getPricingPanel(),
                    BiFunction<List<SoftwareStatusEntity>, Response<BaseResponse>, List<SoftwareStatusEntity>> { t1, t2 ->
                        val listFromDatabase: MutableList<SoftwareStatusEntity> = t1.toMutableList()
                        val response = t2.body()

                        if (response != null && response.isSuccessful) {
                            if (response.data.asJsonObject.has(Constants.JsonKeys.BANK)
                                && !(response.data.asJsonObject.get(Constants.JsonKeys.BANK).isJsonNull)
                            ) {
                                val activeBankArray = response
                                    .data
                                    .asJsonObject
                                    .get(Constants.JsonKeys.BANK)
                                    .asJsonObject
                                    .getAsJsonArray(Constants.JsonKeys.ACTIVE)

                                val pendingBankArray = response
                                    .data
                                    .asJsonObject
                                    .get(Constants.JsonKeys.BANK)
                                    .asJsonObject
                                    .getAsJsonArray(Constants.JsonKeys.PENDING)

                                val adminPendingBankArray = response
                                    .data
                                    .asJsonObject
                                    .get(Constants.JsonKeys.BANK)
                                    .asJsonObject
                                    .getAsJsonArray(Constants.JsonKeys.ADMIN_PENDING)

                                activeBankArray.forEach { bankElement ->
                                    listFromDatabase.forEach { databaseElement ->
                                        if ((databaseElement.softwareID ==
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.SOFTWARE_ID)
                                                        .asString)
                                            && (databaseElement.status ==
                                                    SoftwareStatusEntity.NEUTRAL)
                                        ) {
                                            databaseElement.status = SoftwareStatusEntity.ACTIVE

                                            if (!(bankElement.asJsonObject
                                                    .get(Constants.JsonKeys.STARTED_AT).isJsonNull)
                                            ) {
                                                databaseElement.startingDate =
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.STARTED_AT)
                                                        .asString
                                            }

                                            if (!(bankElement.asJsonObject
                                                    .get(Constants.JsonKeys.EXPIRES_AT).isJsonNull)
                                            ) {
                                                databaseElement.endingDate =
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.EXPIRES_AT)
                                                        .asString
                                            }

                                            if (!(bankElement.asJsonObject
                                                    .get(Constants.JsonKeys.ID).isJsonNull)
                                            ) {
                                                databaseElement.bankSubscriptionID =
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.ID)
                                                        .asString
                                            }
                                        }
                                    }
                                }

                                pendingBankArray.forEach { bankElement ->
                                    listFromDatabase.forEach { databaseElement ->
                                        if ((databaseElement.softwareID ==
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.SOFTWARE_ID)
                                                        .asString)
                                            && (databaseElement.status ==
                                                    SoftwareStatusEntity.NEUTRAL)
                                        ) {
                                            databaseElement.status =
                                                SoftwareStatusEntity.BANK_PENDING

                                            if (!(bankElement.asJsonObject
                                                    .get(Constants.JsonKeys.STARTED_AT).isJsonNull)
                                            ) {
                                                databaseElement.startingDate =
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.STARTED_AT)
                                                        .asString
                                            }

                                            if (!(bankElement.asJsonObject
                                                    .get(Constants.JsonKeys.EXPIRES_AT).isJsonNull)
                                            ) {
                                                databaseElement.endingDate =
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.EXPIRES_AT)
                                                        .asString
                                            }

                                            if (!(bankElement.asJsonObject
                                                    .get(Constants.JsonKeys.ID).isJsonNull)
                                            ) {
                                                databaseElement.bankSubscriptionID =
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.ID)
                                                        .asString
                                            }
                                        }
                                    }
                                }

                                adminPendingBankArray.forEach { bankElement ->
                                    listFromDatabase.forEach { databaseElement ->
                                        if ((databaseElement.softwareID ==
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.SOFTWARE_ID)
                                                        .asString)
                                            && (databaseElement.status ==
                                                    SoftwareStatusEntity.NEUTRAL)
                                        ) {
                                            databaseElement.status =
                                                SoftwareStatusEntity.ADMIN_PENDING

                                            if (!(bankElement.asJsonObject
                                                    .get(Constants.JsonKeys.STARTED_AT).isJsonNull)
                                            ) {
                                                databaseElement.startingDate =
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.STARTED_AT)
                                                        .asString
                                            }

                                            if (!(bankElement.asJsonObject
                                                    .get(Constants.JsonKeys.EXPIRES_AT).isJsonNull)
                                            ) {
                                                databaseElement.endingDate =
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.EXPIRES_AT)
                                                        .asString
                                            }

                                            if (!(bankElement.asJsonObject
                                                    .get(Constants.JsonKeys.ID).isJsonNull)
                                            ) {
                                                databaseElement.bankSubscriptionID =
                                                    bankElement.asJsonObject
                                                        .get(Constants.JsonKeys.ID)
                                                        .asString
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        listFromDatabase
                    })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    dialog?.dismiss()
                    mvpView?.onGettingSoftwareStatusList(it)
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

    fun uploadBankReceipt(
        context: Context,
        bankSubscriptionID: String,
        imageFile: File,
        isPdf: Boolean
    ) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().uploadBankReceipt(
                bankSubscriptionID,
                if (isPdf) {
                    imageFile
                } else {
                    Compressor(context).compressToFile(imageFile)
                },
                isPdf
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
                                ToastUtils.success(response.message)
                                mvpView?.onUploadingBankReceipt()
                            } else {
                                mvpView?.onError(response.message)
                            }
                        } else {
                            mvpView?.onError(DataUtils.getString(R.string.software_license_error_upload_bank_receipt))
                        }
                    }
                }, {
                    dialog?.dismiss()
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.software_license_error_upload_bank_receipt))
                    }
                })
        )
    }
}