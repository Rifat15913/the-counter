package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mtcoredetails.container

import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter

class MtcoreDetailsContainerPresenter : BasePresenter<MtcoreDetailsContainerMvpView>() {
    var walletID: String? = null
    var walletBalance: String? = null
}