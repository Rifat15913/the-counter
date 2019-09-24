package com.diaryofrifat.thecounter.main.data.localandremote.model.software

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.libs.room.BaseEntity

@Entity(
    tableName = Constants.TableNames.SOFTWARE,
    indices = [Index(value = [Constants.ColumnNames.SOFTWARE_ID], unique = true)]
)
data class SoftwareStatusEntity(
    @SerializedName(Constants.JsonKeys.ID)
    @ColumnInfo(name = Constants.ColumnNames.SOFTWARE_ID)
    val softwareID: String,
    @SerializedName(Constants.JsonKeys.TITLE)
    @ColumnInfo(name = Constants.ColumnNames.TITLE)
    val title: String,
    @SerializedName(Constants.JsonKeys.DESCRIPTION)
    @ColumnInfo(name = Constants.ColumnNames.DESCRIPTION)
    val description: String,
    @SerializedName(Constants.JsonKeys.SPEED_PER_SECOND)
    @ColumnInfo(name = Constants.ColumnNames.SPEED_PER_SECOND)
    val speedPerSecond: Int,
    @SerializedName(Constants.JsonKeys.CORES)
    @ColumnInfo(name = Constants.ColumnNames.CORES)
    val cores: Int,
    @SerializedName(Constants.JsonKeys.CONSUMER_RANKING_POINTS)
    @ColumnInfo(name = Constants.ColumnNames.CONSUMER_RANKING_POINTS)
    val consumerRankingPoints: Int,
    @SerializedName(Constants.JsonKeys.PRICE_PER_MONTH)
    @ColumnInfo(name = Constants.ColumnNames.PRICE_PER_MONTH)
    val pricePerMonth: Int,
    @SerializedName(Constants.JsonKeys.PRICE_PER_YEAR)
    @ColumnInfo(name = Constants.ColumnNames.PRICE_PER_YEAR)
    val pricePerYear: Int,
    @SerializedName(Constants.JsonKeys.IMAGE)
    @ColumnInfo(name = Constants.ColumnNames.IMAGE)
    val imageSubUrl: String
) : BaseEntity() {

    @Ignore
    var startingDate: String? = null
    @Ignore
    var endingDate: String? = null
    @Ignore
    var bankSubscriptionID: String? = null
    @Ignore
    var status: Int = NEUTRAL

    /**
     * Below codes are to make the object parcelable
     * */
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(softwareID)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(speedPerSecond)
        parcel.writeInt(cores)
        parcel.writeInt(consumerRankingPoints)
        parcel.writeInt(pricePerMonth)
        parcel.writeInt(pricePerYear)
        parcel.writeString(imageSubUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SoftwareStatusEntity> {
        const val ACTIVE = 1
        const val BANK_PENDING = 2
        const val ADMIN_PENDING = 3
        const val NEUTRAL = 4

        override fun createFromParcel(parcel: Parcel): SoftwareStatusEntity {
            return SoftwareStatusEntity(parcel)
        }

        override fun newArray(size: Int): Array<SoftwareStatusEntity?> {
            return arrayOfNulls(size)
        }
    }
}