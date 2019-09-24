package com.diaryofrifat.thecounter.main.data.remote.model

import com.google.gson.annotations.SerializedName
import com.diaryofrifat.thecounter.utils.helper.Constants

/**
 * This is model class for bank account summary
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class BankAccountSummary(
    @SerializedName(Constants.JsonKeys.BANK_ACCOUNT_ID)
    val bankID: String,
    @SerializedName(Constants.JsonKeys.NAME)
    val bankName: String
)