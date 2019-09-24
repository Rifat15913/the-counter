package com.diaryofrifat.thecounter.main.data.remote.model

import com.google.gson.annotations.SerializedName
import com.diaryofrifat.thecounter.utils.helper.Constants

/**
 * This is model class for mt-core wallet activity
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class MtcoreWalletActivity(
    @SerializedName(Constants.JsonKeys.TRANSACTION_ID)
    val transactionID: String,
    @SerializedName(Constants.JsonKeys.ADDRESS_TYPE)
    val addressType: Int,
    @SerializedName(Constants.JsonKeys.ADDRESS)
    val address: String,
    @SerializedName(Constants.JsonKeys.AMOUNT)
    val amount: String,
    @SerializedName(Constants.JsonKeys.FEES)
    val fees: String,
    @SerializedName(Constants.JsonKeys.STATUS)
    val status: Int,
    @SerializedName(Constants.JsonKeys.CONFIRMATIONS)
    val confirmations: Int,
    @SerializedName(Constants.JsonKeys.CREATED_AT)
    val time: String,
    val currentPage: Int,
    val lastPage: Int,
    val nextPageUrl: String?
)