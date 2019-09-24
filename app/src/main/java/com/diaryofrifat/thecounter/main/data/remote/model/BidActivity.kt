package com.diaryofrifat.thecounter.main.data.remote.model

/**
 * This is model class for bid activity
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class BidActivity(
    val id: String?,
    val time: String?,
    val amount: String?,
    val currentPage: Int,
    val lastPage: Int,
    val nextPageUrl: String?
)