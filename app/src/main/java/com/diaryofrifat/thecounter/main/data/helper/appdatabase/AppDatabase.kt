package com.diaryofrifat.thecounter.main.data.helper.appdatabase

import android.content.Context
import androidx.room.Database
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.helper.appdatabase.AppDatabase.Companion.sInstance
import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareDao
import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareEntity
import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserDao
import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserEntity
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.libs.room.BaseDatabase

/**
 * This is the database class of the LusoSmile project. If we try to have local database in future,
 * we are providing room database class for our app here.
 * @property sInstance instance of the app database class
 * @author Mohd. Asfaq-E-Azam Rifat
 */
@Database(entities = [UserEntity::class, SoftwareEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : BaseDatabase() {
    abstract fun userDao(): UserDao
    abstract fun softwareDao(): SoftwareDao

    companion object {
        @Volatile
        private var sInstance: AppDatabase? = null

        /**
         * This method returns an instance of the database class
         *
         * @return instance of the database class
         * */
        @Synchronized
        fun on(): AppDatabase? {
            return sInstance
        }

        /**
         * This method initializes the database class
         * @param context application context
         * */
        fun init(context: Context) {
            synchronized(AppDatabase::class.java) {
                sInstance = createDb(context,
                        DataUtils.getString(R.string.app_name), AppDatabase::class.java)
            }
        }
    }
}
