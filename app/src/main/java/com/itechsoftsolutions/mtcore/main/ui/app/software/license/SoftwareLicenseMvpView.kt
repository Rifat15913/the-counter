package com.itechsoftsolutions.mtcore.main.ui.app.software.license

import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareStatusEntity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface SoftwareLicenseMvpView : MvpView {
    fun onError(message: String)
    fun onGettingSoftwareStatusList(list: List<SoftwareStatusEntity>)
    fun onUploadingBankReceipt()
}