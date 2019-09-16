package com.itechsoftsolutions.mtcore.main.data.remote.model

import com.google.gson.annotations.SerializedName
import com.itechsoftsolutions.mtcore.utils.helper.Constants

/**
 * This is model class for profile activity
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class ProfileActivity(
    @SerializedName(Constants.JsonKeys.ID)
    val id: Long,
    @SerializedName(Constants.JsonKeys.ACTION)
    val action: String,
    @SerializedName(Constants.JsonKeys.IP_ADDRESS)
    val ipAddress: String,
    @SerializedName(Constants.JsonKeys.SOURCE)
    val source: String,
    @SerializedName(Constants.JsonKeys.LOCATION)
    val location: String,
    @SerializedName(Constants.JsonKeys.CREATED_AT)
    val time: String,
    val currentPage: Int,
    val lastPage: Int,
    val nextPageUrl: String?
)