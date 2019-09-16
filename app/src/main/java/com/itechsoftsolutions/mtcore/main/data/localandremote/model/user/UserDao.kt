package com.itechsoftsolutions.mtcore.main.data.localandremote.model.user

import androidx.room.Dao
import androidx.room.Query
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.libs.room.BaseDao
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UserDao : BaseDao<UserEntity> {
    @Query(
        "SELECT * FROM " + Constants.TableNames.USER
                + " WHERE " + Constants.ColumnNames.USER_ID + " = :userID"
    )
    fun getUser(userID: String): Flowable<UserEntity>

    @Query("DELETE FROM " + Constants.TableNames.USER)
    fun deleteTableData(): Completable
}