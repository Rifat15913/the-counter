package com.diaryofrifat.thecounter.main.ui.app.software.history

import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareEntity
import com.diaryofrifat.thecounter.main.data.remote.model.SoftwareLicensePurchaseHistory
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface SoftwareLicenceHistoryMvpView : MvpView {
    fun onGettingSoftwares(list: List<SoftwareEntity>)
    fun onError(message: String)
    fun onGettingSoftwareLicensePurchaseHistory(list: List<SoftwareLicensePurchaseHistory>)
}