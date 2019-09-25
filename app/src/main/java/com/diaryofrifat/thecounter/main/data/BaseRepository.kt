package com.diaryofrifat.thecounter.main.data

import android.content.Context
import com.diaryofrifat.thecounter.main.data.local.AppLocalDataSource
import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareEntity
import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserEntity
import com.diaryofrifat.thecounter.main.data.remote.AppRemoteDataSource
import io.reactivex.Completable

/**
 * This is the repository class of the project. This class contains all the basic methods needed
 * for the project purposes.
 * @author Mohd. Asfaq-E-Azam Rifat
 */
class BaseRepository(context: Context) {
    private val mAppLocalDataSource = AppLocalDataSource(context)
    private val mAppRemoteDataSource = AppRemoteDataSource()

    companion object {
        private lateinit var sInstance: BaseRepository

        /**
         * This method returns an instance of the this class
         *
         * @return instance of the this class
         * */
        fun on(): BaseRepository {
            return sInstance
        }

        /**
         * This method initializes the class
         * @param context application context
         * */
        @Synchronized
        fun init(context: Context) {
            synchronized(BaseRepository::class.java) {
                sInstance = BaseRepository(context)
            }
        }
    }

    fun insertUserToDatabase(entity: UserEntity): Completable {
        return mAppLocalDataSource.insertCompletable(entity)
    }

    /**
     * This method inserts list of software into database
     * @return stream of the states
     * */
    fun insertSoftwaresToDatabase(list: List<SoftwareEntity>): Completable {
        return mAppLocalDataSource.insertBulkSoftware(list)
    }
}
