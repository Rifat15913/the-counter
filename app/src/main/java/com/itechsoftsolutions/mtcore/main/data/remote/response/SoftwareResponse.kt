package com.itechsoftsolutions.mtcore.main.data.remote.response

import com.google.gson.annotations.SerializedName
import com.itechsoftsolutions.mtcore.main.data.remote.model.SoftwareCollection
import com.itechsoftsolutions.mtcore.utils.helper.Constants

/**
 * This is model class for response of softwares
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class SoftwareResponse(
    @SerializedName(Constants.JsonKeys.SUCCESS) var isSuccessful: Boolean,
    @SerializedName(Constants.JsonKeys.DATA) var data: SoftwareCollection,
    @SerializedName(Constants.JsonKeys.MESSAGE) var message: String
)