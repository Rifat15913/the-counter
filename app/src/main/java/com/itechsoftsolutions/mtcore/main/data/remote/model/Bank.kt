package com.itechsoftsolutions.mtcore.main.data.remote.model

import com.google.gson.annotations.SerializedName
import com.itechsoftsolutions.mtcore.utils.helper.Constants

/**
 * This is model class for bank details
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class Bank(
    @SerializedName(Constants.JsonKeys.ID)
    val bankID: String,
    @SerializedName(Constants.JsonKeys.NAME)
    val bankName: String,
    @SerializedName(Constants.JsonKeys.ADDRESS)
    val bankAddress: String,
    @SerializedName(Constants.JsonKeys.COUNTRY)
    val bankCountry: String,
    @SerializedName(Constants.JsonKeys.SWIFT_CODE)
    val bankSwiftCode: String,
    @SerializedName(Constants.JsonKeys.HOLDER_NAME)
    val holderName: String,
    @SerializedName(Constants.JsonKeys.HOLDER_ADDRESS)
    val holderAddress: String,
    @SerializedName(Constants.JsonKeys.HOLDER_IBAN)
    val holderIBAN: String
)