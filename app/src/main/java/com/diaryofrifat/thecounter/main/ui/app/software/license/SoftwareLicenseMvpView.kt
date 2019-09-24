package com.diaryofrifat.thecounter.main.ui.app.software.license

import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareStatusEntity
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface SoftwareLicenseMvpView : MvpView {
    fun onError(message: String)
    fun onGettingSoftwareStatusList(list: List<SoftwareStatusEntity>)
    fun onUploadingBankReceipt()
}