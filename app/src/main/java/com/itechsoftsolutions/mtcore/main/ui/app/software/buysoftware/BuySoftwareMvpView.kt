package com.itechsoftsolutions.mtcore.main.ui.app.software.buysoftware

import com.itechsoftsolutions.mtcore.main.data.remote.model.Bank
import com.itechsoftsolutions.mtcore.main.data.remote.model.FeesAndVat
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface BuySoftwareMvpView : MvpView {
    fun onError(message: String)
    fun onGettingSoftwareLicenseDetails(
        referenceID: String,
        bankList: List<Bank>,
        feesAndVat: FeesAndVat
    )

    fun onPlacingOrder(message: String)
}