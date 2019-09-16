package com.itechsoftsolutions.mtcore.main.data.remote.response

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.itechsoftsolutions.mtcore.utils.helper.Constants

/**
 * This is model class for response of login page
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class LoginResponse(
        @SerializedName(Constants.JsonKeys.SUCCESS) var isSuccessful: Boolean,
        @SerializedName(Constants.JsonKeys.DATA) var data: JsonElement,
        @SerializedName(Constants.JsonKeys.MESSAGE) var message: String)