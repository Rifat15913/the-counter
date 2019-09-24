package com.diaryofrifat.thecounter.main.data.remote.model

import com.google.gson.annotations.SerializedName
import com.diaryofrifat.thecounter.utils.helper.Constants

/**
 * This is model class for bank details with pagination
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class BankWithPage(
    @SerializedName(Constants.JsonKeys.ID)
    val bankAccountID: String,
    @SerializedName(Constants.JsonKeys.NAME)
    val bankName: String,
    @SerializedName(Constants.JsonKeys.ADDRESS)
    val bankAddress: String?,
    @SerializedName(Constants.JsonKeys.COUNTRY)
    val bankCountry: String?,
    @SerializedName(Constants.JsonKeys.SWIFT_CODE)
    val bankSwiftCode: String?,
    @SerializedName(Constants.JsonKeys.STATUS)
    val status: String,
    @SerializedName(Constants.JsonKeys.HOLDER_NAME)
    val holderName: String?,
    @SerializedName(Constants.JsonKeys.HOLDER_ADDRESS)
    val holderAddress: String?,
    @SerializedName(Constants.JsonKeys.HOLDER_IBAN)
    val holderIBAN: String?,
    var currentPage: Int = 0,
    var lastPage: Int = 0,
    var nextPageUrl: String? = null
)