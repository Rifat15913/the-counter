package com.itechsoftsolutions.mtcore.main.data.remote.model

/**
 * This is model class for software license purchase history
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class SoftwareLicensePurchaseHistory(
    val id: String?,
    val softwareID: String?,
    val price: Long,
    val status: String?,
    val time: String?,
    val currentPage: Int,
    val lastPage: Int,
    val nextPageUrl: String?
)