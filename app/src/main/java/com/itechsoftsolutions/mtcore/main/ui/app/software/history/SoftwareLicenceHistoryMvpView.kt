package com.itechsoftsolutions.mtcore.main.ui.app.software.history

import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareEntity
import com.itechsoftsolutions.mtcore.main.data.remote.model.SoftwareLicensePurchaseHistory
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface SoftwareLicenceHistoryMvpView : MvpView {
    fun onGettingSoftwares(list: List<SoftwareEntity>)
    fun onError(message: String)
    fun onGettingSoftwareLicensePurchaseHistory(list: List<SoftwareLicensePurchaseHistory>)
}