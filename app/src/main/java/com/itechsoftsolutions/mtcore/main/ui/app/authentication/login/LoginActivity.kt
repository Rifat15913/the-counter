package com.itechsoftsolutions.mtcore.main.ui.app.authentication.login

import android.content.Context
import android.content.Intent
import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import android.text.InputType
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.databinding.ActivityLoginBinding
import com.itechsoftsolutions.mtcore.main.ui.app.authentication.forgotpassword.ForgotPasswordActivity
import com.itechsoftsolutions.mtcore.main.ui.app.authentication.registration.RegistrationActivity
import com.itechsoftsolutions.mtcore.main.ui.app.authentication.verification.CodeVerificationActivity
import com.itechsoftsolutions.mtcore.main.ui.app.landing.container.ContainerActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseActivity
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.setRipple
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.KeyboardUtils
import com.itechsoftsolutions.mtcore.utils.helper.SharedPrefUtils
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import java.util.regex.Pattern


class LoginActivity : BaseActivity<LoginMvpView, LoginPresenter>(), LoginMvpView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            runCurrentActivity(context, Intent(context, LoginActivity::class.java))
        }
    }

    private lateinit var mBinding: ActivityLoginBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override fun getActivityPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as ActivityLoginBinding

        initialize()
        setListeners()
    }

    private fun initialize() {
        val desiredInputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        mBinding.editTextPassword.inputType = desiredInputType
        mBinding.editTextPassword.typeface = mBinding.editTextEmail.typeface

        mBinding.textInputLayoutEmail.hint = getString(R.string.registration_email)
        mBinding.textInputLayoutPassword.hint = getString(R.string.registration_password)

        mBinding.textViewForgotPassword.paintFlags =
            mBinding.textViewForgotPassword.paintFlags or UNDERLINE_TEXT_FLAG

        mBinding.textViewRegisterNow.paintFlags =
            mBinding.textViewRegisterNow.paintFlags or UNDERLINE_TEXT_FLAG

        mBinding.textViewLogin.setRipple(R.color.colorWhite50)
        mBinding.textViewRegisterNow.setRipple(R.color.colorPrimary50)
        mBinding.textViewForgotPassword.setRipple(R.color.colorWhite50)
    }

    private fun setListeners() {
        setClickListener(
            mBinding.textViewForgotPassword,
            mBinding.textViewLogin,
            mBinding.textViewRegisterNow
        )
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.text_view_forgot_password -> {
                ForgotPasswordActivity.startActivity(this)
            }

            R.id.text_view_register_now -> {
                RegistrationActivity.startActivity(this)
                finish()
            }

            R.id.text_view_login -> {
                goForLogin()
            }
        }
    }

    private fun goForLogin() {
        if (TextUtils.isEmpty(mBinding.editTextEmail.text.toString().trim())
            || TextUtils.isEmpty(mBinding.editTextPassword.text.toString().trim())
        ) {
            showAlert(getString(R.string.login_error_valid_fields))
        } else {
            if (!(Patterns.EMAIL_ADDRESS.matcher(mBinding.editTextEmail.text.toString().trim()).matches())) {
                showAlert(getString(R.string.login_error_valid_email))
            } else if (mBinding.editTextPassword.text.toString().trim().length <
                DataUtils.getInteger(R.integer.character_min_limit_password)
            ) {
                showAlert(getString(R.string.login_error_valid_password))
            } else if (!(Pattern.compile(Constants.Common.REGEX_PASSWORD)
                    .matcher(mBinding.editTextPassword.text.toString().trim())
                    .matches())
            ) {
                showAlert(getString(R.string.login_error_proper_password))
            } else {
                KeyboardUtils.hideKeyboard(this)
                presenter.login(
                    this, mBinding.editTextEmail.text.toString().trim(),
                    mBinding.editTextPassword.text.toString().trim()
                )
            }
        }
    }

    private fun showAlert(message: String) {
        ToastUtils.error(message, true)
    }

    override fun stopUI() {

    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()

        if (SharedPrefUtils.readBoolean(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON)) {
            CodeVerificationActivity.startActivity(this, CodeVerificationActivity.COMING_FROM_LOGIN)
        } else {
            ContainerActivity.startActivity(this)
            finish()
        }
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}