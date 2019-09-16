package com.itechsoftsolutions.mtcore.main.ui.app.ranking.myranking

import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareEntity
import com.itechsoftsolutions.mtcore.main.data.remote.model.MyRanking
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface MyRankingMvpView : MvpView {
    fun onGettingRankings(
        sellerMonthlyPoints: String,
        bideratorMonthlyPoints: String,
        bideratorYearlyPoints: String,
        consumerMonthlyPoints: String,
        consumerYearlyPoints: String
    )

    fun onGettingSellerHistory(list: List<MyRanking>)
    fun onGettingConsumerHistory(list: List<MyRanking>)
    fun onGettingBideratorHistory(list: List<MyRanking>)
    fun onGettingSoftwares(list: List<SoftwareEntity>)
    fun onError(message: String)
}