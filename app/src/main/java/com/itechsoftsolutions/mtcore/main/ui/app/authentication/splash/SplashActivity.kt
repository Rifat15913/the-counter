package com.itechsoftsolutions.mtcore.main.ui.app.authentication.splash

import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.ui.app.authentication.welcome.WelcomeActivity
import com.itechsoftsolutions.mtcore.main.ui.app.landing.container.ContainerActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseActivity
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.SharedPrefUtils

class SplashActivity : BaseActivity<SplashMvpView, SplashPresenter>(), SplashMvpView {
    override val layoutResourceId: Int
        get() = INVALID_ID

    override fun getActivityPresenter(): SplashPresenter {
        return SplashPresenter()
    }

    override fun startUI() {
        val isGoogleAuthOn =
            SharedPrefUtils.readBoolean(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON)

        val isGoogleAuthVerified =
            SharedPrefUtils.readBoolean(Constants.PreferenceKeys.IS_GOOGLE_AUTH_VERIFIED)

        val isLoggedIn =
            SharedPrefUtils.readBoolean(Constants.PreferenceKeys.LOGGED_IN)

        if (isGoogleAuthOn) {
            if (isGoogleAuthVerified) {
                ContainerActivity.startActivity(this)
            } else {
                if (isLoggedIn) {
                    presenter.compositeDisposable.add(
                        BaseRepository.on().logOut(this)
                    )
                } else {
                    WelcomeActivity.startActivity(this)
                }
            }
        } else {
            if (isLoggedIn) {
                ContainerActivity.startActivity(this)
            } else {
                WelcomeActivity.startActivity(this)
            }
        }
    }

    override fun stopUI() {

    }
}