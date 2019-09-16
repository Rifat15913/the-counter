package com.itechsoftsolutions.mtcore.main.data.remote.model

/**
 * This is model class for my ranking history
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class MyRanking(
    val type: Int,
    val points: Long,
    val softwareID: String?,
    val productType: Int?,
    val time: String?,
    val currentPage: Int,
    val lastPage: Int,
    val nextPageUrl: String?,
    val title: String? = null
) {
    companion object {
        const val SELLER = 10
        const val CONSUMER = 11
        const val BIDERATOR = 12
    }
}