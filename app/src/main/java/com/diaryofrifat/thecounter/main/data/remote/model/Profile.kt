package com.diaryofrifat.thecounter.main.data.remote.model

import com.google.gson.annotations.SerializedName
import com.diaryofrifat.thecounter.utils.helper.Constants

/**
 * This is model class for profile
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class Profile(
    @SerializedName(Constants.JsonKeys.ID)
    val userID: String,
    @SerializedName(Constants.JsonKeys.FIRST_NAME)
    val firstName: String,
    @SerializedName(Constants.JsonKeys.LAST_NAME)
    val lastName: String,
    @SerializedName(Constants.JsonKeys.COUNTRY)
    val country: String? = null,
    @SerializedName(Constants.JsonKeys.PHONE_NUMBER)
    val phoneNumber: String? = null,
    @SerializedName(Constants.JsonKeys.PHOTO)
    val photo: String? = null,
    @SerializedName(Constants.JsonKeys.GENDER)
    val gender: String? = null,
    @SerializedName(Constants.JsonKeys.MARITAL_STATE)
    val maritalState: String? = null,
    @SerializedName(Constants.JsonKeys.BIRTH_DATE)
    val birthDate: String? = null,
    @SerializedName(Constants.JsonKeys.ID_OR_PASSPORT_NUMBER)
    val idOrPasswordNumber: String? = null
)