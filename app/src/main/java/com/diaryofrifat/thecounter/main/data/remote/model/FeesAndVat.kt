package com.diaryofrifat.thecounter.main.data.remote.model

import com.google.gson.annotations.SerializedName
import com.diaryofrifat.thecounter.utils.helper.Constants

/**
 * This is model class for fees and vat details
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class FeesAndVat(
    @SerializedName(Constants.JsonKeys.FEES)
    val fees: Double,
    @SerializedName(Constants.JsonKeys.VAT)
    val vat: Double,
    @SerializedName(Constants.JsonKeys.MONTHLY_PRICE_FEES)
    val monthlyPriceFees: Double,
    @SerializedName(Constants.JsonKeys.MONTHLY_PRICE_VAT)
    val monthlyPriceVat: Double,
    @SerializedName(Constants.JsonKeys.YEARLY_PRICE_FEES)
    val yearlyPriceFees: Double,
    @SerializedName(Constants.JsonKeys.YEARLY_PRICE_VAT)
    val yearlyPriceVat: Double,
    @SerializedName(Constants.JsonKeys.MONTHLY_TOTAL_PRICE)
    val monthlyTotalPrice: Double,
    @SerializedName(Constants.JsonKeys.YEARLY_TOTAL_PRICE)
    val yearlyTotalPrice: Double
)