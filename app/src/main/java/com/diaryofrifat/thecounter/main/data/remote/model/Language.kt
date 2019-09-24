package com.diaryofrifat.thecounter.main.data.remote.model

import com.google.gson.annotations.SerializedName
import com.diaryofrifat.thecounter.utils.helper.Constants

/**
 * This is model class for languages
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class Language(
    @SerializedName(Constants.JsonKeys.CODE)
    val code: String,
    @SerializedName(Constants.JsonKeys.LANG)
    val language: String
)