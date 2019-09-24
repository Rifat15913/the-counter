package com.diaryofrifat.thecounter.main.ui.app.profile.bank

import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserEntity
import com.diaryofrifat.thecounter.main.data.remote.model.BankWithPage
import com.diaryofrifat.thecounter.main.ui.base.callback.MvpView

interface BankDetailsMvpView : MvpView {
    fun onError(message: String)
    fun onAddition(message: String)
    fun onGettingBankList(list: List<BankWithPage>)
    fun onGettingUser(user: UserEntity)
    fun onGettingCountryList(list: List<String>)
}