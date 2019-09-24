package com.diaryofrifat.thecounter.main.ui.app.software.buysoftware

import com.diaryofrifat.thecounter.main.data.remote.model.Bank
import com.diaryofrifat.thecounter.main.data.remote.model.FeesAndVat
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface BuySoftwareMvpView : MvpView {
    fun onError(message: String)
    fun onGettingSoftwareLicenseDetails(
        referenceID: String,
        bankList: List<Bank>,
        feesAndVat: FeesAndVat
    )

    fun onPlacingOrder(message: String)
}