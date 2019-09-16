package com.itechsoftsolutions.mtcore.main.ui.app.myreferral

import com.itechsoftsolutions.mtcore.main.ui.base.callback.MvpView

interface MyReferralMvpView : MvpView {
    fun onSuccess(url: String)
    fun onError(message: String)
}