package com.diaryofrifat.thecounter.main.data.localandremote.model.software

import androidx.room.Dao
import androidx.room.Query
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.libs.room.BaseDao
import io.reactivex.Flowable

@Dao
interface SoftwareDao : BaseDao<SoftwareEntity> {
    @Query("SELECT * FROM " + Constants.TableNames.SOFTWARE)
    fun getSoftwareList(): Flowable<List<SoftwareEntity>>

    @Query("SELECT * FROM " + Constants.TableNames.SOFTWARE)
    fun getSoftwareStatusList(): Flowable<List<SoftwareStatusEntity>>
}