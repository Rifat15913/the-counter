package com.diaryofrifat.thecounter.main.ui.app.authentication.splash

import com.diaryofrifat.thecounter.main.ui.app.landing.container.ContainerActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseActivity

class SplashActivity : BaseActivity<SplashMvpView, SplashPresenter>(), SplashMvpView {
    override val layoutResourceId: Int
        get() = INVALID_ID

    override fun getActivityPresenter(): SplashPresenter {
        return SplashPresenter()
    }

    override fun startUI() {
        ContainerActivity.startActivity(this)
    }

    override fun stopUI() {

    }
}