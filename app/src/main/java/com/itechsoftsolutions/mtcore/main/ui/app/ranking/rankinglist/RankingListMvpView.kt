package com.itechsoftsolutions.mtcore.main.ui.app.ranking.rankinglist

import com.itechsoftsolutions.mtcore.main.data.remote.model.Ranker
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface RankingListMvpView : MvpView {
    fun onError(message: String)
    fun onGettingData(list: List<Ranker>)
}