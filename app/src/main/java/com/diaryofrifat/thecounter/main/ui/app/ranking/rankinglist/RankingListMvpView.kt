package com.diaryofrifat.thecounter.main.ui.app.ranking.rankinglist

import com.diaryofrifat.thecounter.main.data.remote.model.Ranker
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface RankingListMvpView : MvpView {
    fun onError(message: String)
    fun onGettingData(list: List<Ranker>)
}