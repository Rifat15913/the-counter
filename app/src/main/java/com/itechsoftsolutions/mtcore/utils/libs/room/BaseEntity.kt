package com.itechsoftsolutions.mtcore.utils.libs.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.itechsoftsolutions.mtcore.utils.helper.Constants

/**
 * This is a base entity class that should be extended when we need to create a room database entity
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
abstract class BaseEntity : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.ColumnNames.ID)
    @Transient
    var id: Long = 0
}
