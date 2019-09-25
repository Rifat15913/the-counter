package com.diaryofrifat.thecounter.main.data.local

import android.content.Context
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.helper.appdatabase.AppDatabase
import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareDao
import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareEntity
import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareStatusEntity
import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserDao
import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserEntity
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import io.reactivex.Completable
import io.reactivex.Flowable

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
}