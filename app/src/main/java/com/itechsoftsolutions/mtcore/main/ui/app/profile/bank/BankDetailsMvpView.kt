package com.itechsoftsolutions.mtcore.main.ui.app.profile.bank

import com.itechsoftsolutions.mtcore.main.data.localandremote.model.user.UserEntity
import com.itechsoftsolutions.mtcore.main.data.remote.model.BankWithPage
import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface BankDetailsMvpView : MvpView {
    fun onError(message: String)
    fun onAddition(message: String)
    fun onGettingBankList(list: List<BankWithPage>)
    fun onGettingUser(user: UserEntity)
    fun onGettingCountryList(list: List<String>)
}