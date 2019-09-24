package com.diaryofrifat.thecounter.main.ui.app.authentication.registration

import android.content.Context
import android.text.TextUtils
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.BaseRepository
import com.diaryofrifat.thecounter.main.ui.base.component.BasePresenter
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*


class RegistrationPresenter : BasePresenter<RegistrationMvpView>() {

    var countryList: MutableList<String> = ArrayList()

    fun register(
        context: Context, firstName: String, lastName: String,
        email: String, password: String, country: String
    ) {
        ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().register(firstName, lastName, email, password, country)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val baseResponse = it.body()

                    if (baseResponse == null) {
                        mvpView?.onError(DataUtils.getString(R.string.registration_error_of_registration))
                    } else {
                        if (baseResponse.isSuccessful) {
                            mvpView?.onSuccess(
                                String.format(
                                    Locale.getDefault(),
                                    DataUtils.getString(R.string.registration_confirmation_of_registration),
                                    email
                                )
                            )
                        } else {
                            mvpView?.onError(baseResponse.message)
                        }
                    }
                }, {
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.registration_error_of_registration))
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