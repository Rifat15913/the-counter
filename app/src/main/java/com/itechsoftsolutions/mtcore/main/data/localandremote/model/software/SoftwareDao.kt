package com.itechsoftsolutions.mtcore.main.data.localandremote.model.software

import androidx.room.Dao
import androidx.room.Query
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.libs.room.BaseDao
import io.reactivex.Flowable

@Dao
interface SoftwareDao : BaseDao<SoftwareEntity> {
    @Query("SELECT * FROM " + Constants.TableNames.SOFTWARE)
    fun getSoftwareList(): Flowable<List<SoftwareEntity>>

    @Query("SELECT * FROM " + Constants.TableNames.SOFTWARE)
    fun getSoftwareStatusList(): Flowable<List<SoftwareStatusEntity>>
}