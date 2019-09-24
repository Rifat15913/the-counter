package com.diaryofrifat.thecounter.main.data.local.model

data class DashboardItem(
    val title: String?,
    val subtitle: String?,
    val resource: Any?,
    val header1: String?,
    val content1: String?,
    val header2: String?,
    val content2: String?,
    val header3: String?,
    val content3: String?,
    val itemSection: Int
) {
    companion object {
        const val BALANCE_SECTION = 1
        const val SHARE_SECTION = 2
        const val TOP_REWARD_SECTION = 3
        const val MY_COMPONENT_SECTION = 4
        const val LAST_MONTH_SECTION = 5
        const val LAST_YEAR_SECTION = 6
        const val STATUS_SECTION = 7
    }
}