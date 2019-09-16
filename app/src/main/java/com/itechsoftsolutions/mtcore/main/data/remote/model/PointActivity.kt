package com.itechsoftsolutions.mtcore.main.data.remote.model

/**
 * This is model class for point activity
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class PointActivity(
    val id: String,
    val point: String,
    val cash: String,
    val bankName: String,
    val adminMessage: String?,
    var status: String,
    val bankReceiptImageUrl: String?,
    val bankAccountID: String,
    val currentPage: Int,
    val lastPage: Int,
    val nextPageUrl: String?
)