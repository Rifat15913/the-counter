package com.itechsoftsolutions.mtcore.main.ui.app.profile.bank

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.user.UserEntity
import com.itechsoftsolutions.mtcore.main.data.remote.model.BankWithPage
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.SharedPrefUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class BankDetailsPresenter : BasePresenter<BankDetailsMvpView>() {
    var countryList: MutableList<String> = ArrayList()

    fun getUserBankList(context: Context, page: Int) {
        compositeDisposable.add(
            BaseRepository.on().getUserBankList(page)
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
                                val bankObject =
                                    response.data.asJsonObject[Constants.JsonKeys.BANKS]

                                val currentPage =
                                    bankObject.asJsonObject[Constants.JsonKeys.CURRENT_PAGE].asInt

                                val lastPage =
                                    bankObject.asJsonObject[Constants.JsonKeys.LAST_PAGE].asInt

                                val nextPageUrl: String? =
                                    if (bankObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .isJsonNull
                                    ) {
                                        null
                                    } else {
                                        bankObject
                                            .asJsonObject[Constants.JsonKeys.NEXT_PAGE_URL]
                                            .asString
                                    }

                                val list: MutableList<BankWithPage> = ArrayList()

                                bankObject
                                    .asJsonObject[Constants.JsonKeys.DATA]
                                    .asJsonArray
                                    .forEach { element ->
                                        list.add(
                                            BankWithPage(
                                                element.asJsonObject[Constants.JsonKeys.BANK_ACCOUNT_ID].asString,
                                                element.asJsonObject[Constants.JsonKeys.NAME].asString,
                                                if (element.asJsonObject[Constants.JsonKeys.ADDRESS].isJsonNull) {
                                                    null
                                                } else {
                                                    element.asJsonObject[Constants.JsonKeys.ADDRESS].asString
                                                },
                                                if (element.asJsonObject[Constants.JsonKeys.COUNTRY].isJsonNull) {
                                                    null
                                                } else {
                                                    element.asJsonObject[Constants.JsonKeys.COUNTRY].asString
                                                },
                                                if (element.asJsonObject[Constants.JsonKeys.SWIFT_CODE].isJsonNull) {
                                                    null
                                                } else {
                                                    element.asJsonObject[Constants.JsonKeys.SWIFT_CODE].asString
                                                },
                                                element.asJsonObject[Constants.JsonKeys.STATUS].asString,
                                                if (element.asJsonObject[Constants.JsonKeys.HOLDER_NAME].isJsonNull) {
                                                    null
                                                } else {
                                                    element.asJsonObject[Constants.JsonKeys.HOLDER_NAME].asString
                                                },
                                                if (element.asJsonObject[Constants.JsonKeys.HOLDER_ADDRESS].isJsonNull) {
                                                    null
                                                } else {
                                                    element.asJsonObject[Constants.JsonKeys.HOLDER_ADDRESS].asString
                                                },
                                                if (element.asJsonObject[Constants.JsonKeys.HOLDER_IBAN].isJsonNull) {
                                                    null
                                                } else {
                                                    element.asJsonObject[Constants.JsonKeys.HOLDER_IBAN].asString
                                                },
                                                currentPage,
                                                lastPage,
                                                nextPageUrl
                                            )
                                        )
                                    }

                                mvpView?.onGettingBankList(list)
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

    fun addBankAccount(
        context: Context,
        bankName: String,
        bankAddress: String,
        bankCountry: String,
        swiftCode: String,
        holderName: String,
        holderAddress: String,
        holderIBAN: String,
        acceptTerms: String
    ) {
        val dialog = ProgressDialogUtils.on().showProgressDialog(context)
        compositeDisposable.add(
            BaseRepository.on().addBankAccount(
                bankName, bankAddress, bankCountry, swiftCode,
                holderName, holderAddress, holderIBAN, acceptTerms
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
                                mvpView?.onAddition(response.message)
                            } else {
                                mvpView?.onError(response.message)
                            }
                        } else {
                            mvpView?.onError(DataUtils.getString(R.string.error_something_went_wrong))
                        }
                    }
                }, {
                    dialog?.dismiss()
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.error_something_went_wrong))
                    }

                })
        )
    }

    fun getUserFromDatabase() {
        compositeDisposable.add(
            BaseRepository.on().getUserFromDatabase(
                SharedPrefUtils.readString(Constants.PreferenceKeys.USER_ID)
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    mvpView?.onGettingUser(it)
                }, {
                    Timber.d(it)
                })
        )
    }

    fun getUserFromCloud(context: Context) {
        compositeDisposable.add(
            BaseRepository.on().getProfile()
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
                                val userObject =
                                    response.data
                                        .asJsonObject[Constants.JsonKeys.USER]
                                        .asJsonObject

                                val userInfoObject =
                                    response.data
                                        .asJsonObject[Constants.JsonKeys.USER_INFO]
                                        .asJsonObject

                                if (userObject.has(Constants.JsonKeys.ID)) {
                                    SharedPrefUtils.write(
                                        Constants.PreferenceKeys.USER_ID,
                                        userObject[Constants.JsonKeys.ID].asString!!
                                    )
                                }

                                val user =
                                    UserEntity(
                                        userObject[Constants.JsonKeys.ID].asString!!,
                                        userObject[Constants.JsonKeys.FIRST_NAME].asString!!,
                                        userObject[Constants.JsonKeys.LAST_NAME].asString!!,
                                        userObject[Constants.JsonKeys.EMAIL].asString!!,
                                        if (userInfoObject[Constants.JsonKeys.I_AM].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.I_AM].asString
                                        },
                                        if (userObject[Constants.JsonKeys.PHOTO].isJsonNull) {
                                            null
                                        } else {
                                            userObject[Constants.JsonKeys.PHOTO].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.ID_OR_PASSPORT_NUMBER].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.ID_OR_PASSPORT_NUMBER].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.VAT_ID].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.VAT_ID].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.PHONE_NUMBER].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.PHONE_NUMBER].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.COUNTRY].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.COUNTRY].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.FISCAL_COUNTRY].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.FISCAL_COUNTRY].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.GENDER].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.GENDER].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.MARITAL_STATE].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.MARITAL_STATE].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.BIRTH_DATE].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.BIRTH_DATE].asString
                                        }, if (userInfoObject[Constants.JsonKeys.ZIP].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.ZIP].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.CITY].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.CITY].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.STATE].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.STATE].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.NUMBER].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.NUMBER].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.COMPLEMENT].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.COMPLEMENT].asString
                                        },
                                        if (userInfoObject[Constants.JsonKeys.FULL_ADDRESS].isJsonNull) {
                                            null
                                        } else {
                                            userInfoObject[Constants.JsonKeys.FULL_ADDRESS].asString
                                        },
                                        userInfoObject[Constants.JsonKeys.PASSPORT_VERIFIED].asInt,
                                        userInfoObject[Constants.JsonKeys.UTILITY_BILL_VERIFIED].asInt,
                                        userInfoObject[Constants.JsonKeys.NID_VERIFIED].asInt
                                    )

                                compositeDisposable.add(
                                    BaseRepository.on().insertUserToDatabase(user)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .subscribeWith(object : DisposableCompletableObserver() {
                                            override fun onComplete() {
                                                // Do nothing
                                                mvpView?.onGettingUser(user)
                                            }

                                            override fun onError(e: Throwable) {
                                                Timber.d(e)
                                            }
                                        })
                                )
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

    fun getCountryList() {
        compositeDisposable.add(
            BaseRepository.on().getCountryList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val response = it.body()

                    if (response == null) {
                        mvpView?.onError(DataUtils.getString(R.string.profile_error_could_not_load_countries))
                    } else {
                        if (response.isSuccessful) {
                            countryList.clear()

                            response.data
                                .asJsonObject[Constants.JsonKeys.COUNTRIES]
                                .asJsonArray
                                .forEach { element ->
                                    countryList.add(element.asString)
                                }

                            mvpView?.onGettingCountryList(countryList)
                        } else {
                            mvpView?.onError(response.message)
                        }
                    }
                }, {
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.profile_error_could_not_load_countries))
                    }

                })
        )
    }
}