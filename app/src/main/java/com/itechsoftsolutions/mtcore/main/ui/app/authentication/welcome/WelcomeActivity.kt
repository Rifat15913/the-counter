package com.itechsoftsolutions.mtcore.main.ui.app.authentication.welcome

import android.content.Context
import android.content.Intent
import android.view.View
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.app.authentication.login.LoginActivity
import com.itechsoftsolutions.mtcore.main.ui.app.authentication.registration.RegistrationActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseActivity
import com.itechsoftsolutions.mtcore.main.ui.base.setRipple

class WelcomeActivity : BaseActivity<WelcomeMvpView, WelcomePresenter>() {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            runCurrentActivity(context, intent)
        }
    }

    lateinit var mBinding: com.itechsoftsolutions.mtcore.databinding.ActivityWelcomeBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_welcome

    override fun getActivityPresenter(): WelcomePresenter {
        return WelcomePresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as com.itechsoftsolutions.mtcore.databinding.ActivityWelcomeBinding
        setClickListener(mBinding.textViewLogin, mBinding.textViewRegister)
        mBinding.textViewLogin.setRipple(R.color.colorWhite50)
        mBinding.textViewRegister.setRipple(R.color.colorWhite50)
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.text_view_register -> RegistrationActivity.startActivity(this)
            R.id.text_view_login -> LoginActivity.startActivity(this)
        }
    }

    override fun stopUI() {

    }
}