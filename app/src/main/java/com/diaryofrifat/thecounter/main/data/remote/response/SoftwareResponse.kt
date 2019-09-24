package com.diaryofrifat.thecounter.main.data.remote.response

import com.google.gson.annotations.SerializedName
import com.diaryofrifat.thecounter.main.data.remote.model.SoftwareCollection
import com.diaryofrifat.thecounter.utils.helper.Constants

/**
 * This is model class for response of softwares
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class SoftwareResponse(
    @SerializedName(Constants.JsonKeys.SUCCESS) var isSuccessful: Boolean,
    @SerializedName(Constants.JsonKeys.DATA) var data: SoftwareCollection,
    @SerializedName(Constants.JsonKeys.MESSAGE) var message: String
)