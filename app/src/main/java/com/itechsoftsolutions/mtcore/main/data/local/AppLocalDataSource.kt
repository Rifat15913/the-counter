package com.itechsoftsolutions.mtcore.main.data.local

import android.content.Context
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.helper.appdatabase.AppDatabase
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareDao
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareEntity
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareStatusEntity
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.user.UserDao
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.user.UserEntity
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.SharedPrefUtils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * This is the local data source class of the project. This class contains all the basic methods
 * needed for local purposes.
 * @author Mohd. Asfaq-E-Azam Rifat
 */
class AppLocalDataSource(context: Context) {
    private var mUserDao: UserDao? = null
    private var mSoftwareDao: SoftwareDao? = null

    /**
     * This the initialization block that initializes the database and other local services
     * */
    init {
        AppDatabase.init(context)
        mUserDao = AppDatabase.on()?.userDao()
        mSoftwareDao = AppDatabase.on()?.softwareDao()
    }

    fun insertCompletable(entity: UserEntity): Completable {
        return mUserDao?.insert(entity) ?: Completable.error(
            Throwable(
                DataUtils.getString(R.string.error_dao_is_null)
            )
        )
    }

    /**
     * This method inserts list of software into database
     * @return stream of the states
     * */
    fun insertBulkSoftware(list: List<SoftwareEntity>): Completable {
        return mSoftwareDao?.insertBulk(list) ?: Completable.error(
            Throwable(
                DataUtils.getString(R.string.error_dao_is_null)
            )
        )
    }

    /**
     * This method provides list of software
     * @return list of software
     * */
    fun getSoftwareList(): Flowable<List<SoftwareEntity>> {
        return mSoftwareDao?.getSoftwareList() ?: Flowable.error(
            Throwable(
                DataUtils.getString(R.string.error_dao_is_null)
            )
        )
    }

    /**
     * This method provides the current user
     * @return entity of the user
     * */
    fun getUser(userID: String): Flowable<UserEntity> {
        return mUserDao?.getUser(userID) ?: Flowable.error(
            Throwable(
                DataUtils.getString(R.string.error_dao_is_null)
            )
        )
    }

    /**
     * This method deletes all the data of user table
     * */
    fun deleteUserTableData(): Completable {
        return mUserDao?.deleteTableData() ?: Completable.error(
            Throwable(
                DataUtils.getString(R.string.error_dao_is_null)
            )
        )
    }

    /**
     * This method provides list of software with status
     * @return list of software with status
     * */
    fun getSoftwareStatusList(): Flowable<List<SoftwareStatusEntity>> {
        return mSoftwareDao?.getSoftwareStatusList() ?: Flowable.error(
            Throwable(
                DataUtils.getString(R.string.error_dao_is_null)
            )
        )
    }

    /**
     * This method logs out the user
     * @return stream of the states
     * */
    fun logOut(didLogOut: Boolean): Completable {
        return Completable.create {
            if (didLogOut) {
                SharedPrefUtils.write(Constants.PreferenceKeys.LOGGED_IN, false)

                if (SharedPrefUtils.contains(Constants.PreferenceKeys.EMAIL)) {
                    SharedPrefUtils.delete(Constants.PreferenceKeys.EMAIL)
                }

                if (SharedPrefUtils.contains(Constants.PreferenceKeys.FIRST_NAME)) {
                    SharedPrefUtils.delete(Constants.PreferenceKeys.FIRST_NAME)
                }

                if (SharedPrefUtils.contains(Constants.PreferenceKeys.LAST_NAME)) {
                    SharedPrefUtils.delete(Constants.PreferenceKeys.LAST_NAME)
                }

                if (SharedPrefUtils.contains(Constants.PreferenceKeys.PHONE)) {
                    SharedPrefUtils.delete(Constants.PreferenceKeys.PHONE)
                }

                if (SharedPrefUtils.contains(Constants.PreferenceKeys.ACCESS_TYPE)) {
                    SharedPrefUtils.delete(Constants.PreferenceKeys.ACCESS_TYPE)
                }

                if (SharedPrefUtils.contains(Constants.PreferenceKeys.ACCESS_TOKEN)) {
                    SharedPrefUtils.delete(Constants.PreferenceKeys.ACCESS_TOKEN)
                }

                if (SharedPrefUtils.contains(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET)) {
                    SharedPrefUtils.delete(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET)
                }

                if (SharedPrefUtils.contains(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON)) {
                    SharedPrefUtils.delete(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON)
                }

                if (SharedPrefUtils.contains(Constants.PreferenceKeys.REFERRAL_LINK)) {
                    SharedPrefUtils.delete(Constants.PreferenceKeys.REFERRAL_LINK)
                }

                if (SharedPrefUtils.contains(Constants.PreferenceKeys.IS_GOOGLE_AUTH_VERIFIED)) {
                    SharedPrefUtils.delete(Constants.PreferenceKeys.IS_GOOGLE_AUTH_VERIFIED)
                }

                it.onComplete()
            } else {
                if (!it.isDisposed) {
                    it.onError(Throwable())
                }
            }
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}