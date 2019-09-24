package com.diaryofrifat.thecounter.main.data.localandremote.model.user

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.libs.room.BaseEntity

@Entity(
    tableName = Constants.TableNames.USER,
    indices = [Index(value = [Constants.ColumnNames.USER_ID], unique = true)]
)
data class UserEntity(
    @ColumnInfo(name = Constants.ColumnNames.USER_ID)
    val userId: String,
    @ColumnInfo(name = Constants.ColumnNames.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = Constants.ColumnNames.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = Constants.ColumnNames.EMAIL)
    val email: String,
    @ColumnInfo(name = Constants.ColumnNames.I_AM)
    val iAm: String?,
    @ColumnInfo(name = Constants.ColumnNames.PHOTO)
    val photo: String?,
    @ColumnInfo(name = Constants.ColumnNames.ID_OR_PASSPORT_NUMBER)
    val idOrPassportNumber: String?,
    @ColumnInfo(name = Constants.ColumnNames.VAT_ID)
    val vatID: String?,
    @ColumnInfo(name = Constants.ColumnNames.PHONE_NUMBER)
    val phoneNumber: String?,
    @ColumnInfo(name = Constants.ColumnNames.COUNTRY)
    val country: String?,
    @ColumnInfo(name = Constants.ColumnNames.FISCAL_COUNTRY)
    val fiscalCountry: String?,
    @ColumnInfo(name = Constants.ColumnNames.GENDER)
    val gender: String?,
    @ColumnInfo(name = Constants.ColumnNames.MARITAL_STATUS)
    val maritalStatus: String?,
    @ColumnInfo(name = Constants.ColumnNames.DATE_OF_BIRTH)
    val dateOfBirth: String?,
    @ColumnInfo(name = Constants.ColumnNames.ZIP_CODE)
    val zipCode: String?,
    @ColumnInfo(name = Constants.ColumnNames.CITY)
    val city: String?,
    @ColumnInfo(name = Constants.ColumnNames.STATE)
    val state: String?,
    @ColumnInfo(name = Constants.ColumnNames.NUMBER)
    val number: String?,
    @ColumnInfo(name = Constants.ColumnNames.COMPLEMENT)
    val complement: String?,
    @ColumnInfo(name = Constants.ColumnNames.FULL_ADDRESS)
    val fullAddress: String?,
    @ColumnInfo(name = Constants.ColumnNames.IS_PASSPORT_VERIFIED)
    val passportVerificationStatus: Int,
    @ColumnInfo(name = Constants.ColumnNames.IS_UTILITY_BILL_VERIFIED)
    val utilityBillVerificationStatus: Int,
    @ColumnInfo(name = Constants.ColumnNames.IS_NID_VERIFIED)
    val nidVerificationStatus: Int
) : BaseEntity() {

    /**
     * Below codes are to make the object parcelable
     * */
    constructor(parcel: Parcel) :
            this(
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt()
            )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(iAm)
        parcel.writeString(photo)
        parcel.writeString(idOrPassportNumber)
        parcel.writeString(vatID)
        parcel.writeString(phoneNumber)
        parcel.writeString(country)
        parcel.writeString(fiscalCountry)
        parcel.writeString(gender)
        parcel.writeString(maritalStatus)
        parcel.writeString(dateOfBirth)
        parcel.writeString(zipCode)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(number)
        parcel.writeString(complement)
        parcel.writeString(fullAddress)
        parcel.writeInt(passportVerificationStatus)
        parcel.writeInt(utilityBillVerificationStatus)
        parcel.writeInt(nidVerificationStatus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserEntity> {
        override fun createFromParcel(parcel: Parcel): UserEntity {
            return UserEntity(parcel)
        }

        override fun newArray(size: Int): Array<UserEntity?> {
            return arrayOfNulls(size)
        }
    }
}