package com.diaryofrifat.thecounter.main.ui.app.authentication.forgotpassword

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.databinding.ActivityForgotPasswordBinding
import com.diaryofrifat.thecounter.main.ui.app.authentication.verification.CodeVerificationActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseActivity
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.setRipple
import com.diaryofrifat.thecounter.utils.helper.KeyboardUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils

class ForgotPasswordActivity : BaseActivity<ForgotPasswordMvpView, ForgotPasswordPresenter>(),
    ForgotPasswordMvpView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            runCurrentActivity(context, Intent(context, ForgotPasswordActivity::class.java))
        }
    }

    private lateinit var mBinding: ActivityForgotPasswordBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_forgot_password

    override fun getActivityPresenter(): ForgotPasswordPresenter {
        return ForgotPasswordPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as ActivityForgotPasswordBinding
        initialize()
        setListeners()
    }

    private fun initialize() {
        mBinding.textInputLayoutEmail.hint = getString(R.string.registration_email)
        mBinding.textViewRequest.setRipple(R.color.colorWhite50)
        mBinding.imageViewNavigator.setRipple(R.color.colorPrimary50)
    }

    private fun setListeners() {
        setClickListener(mBinding.textViewRequest, mBinding.imageViewNavigator)
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_navigator -> {
                onBackPressed()
            }

            R.id.text_view_request -> {
                goForResettingPassword()
            }
        }
    }

    private fun goForResettingPassword() {
        if (TextUtils.isEmpty(mBinding.editTextEmail.text.toString().trim())) {
            showAlert(getString(R.string.forgot_password_error_valid_field))
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(mBinding.editTextEmail.text.toString().trim()).matches())) {
            showAlert(getString(R.string.forgot_password_error_valid_email))
        } else {
            KeyboardUtils.hideKeyboard(this)
            presenter.resetPassword(this, mBinding.editTextEmail.text.toString().trim())
        }
    }

    private fun showAlert(message: String) {
        ToastUtils.error(message, true)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun stopUI() {

    }

    override fun onSuccess(message: String, token: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        mBinding.editTextEmail.text?.let {
            CodeVerificationActivity.startActivity(
                this,
                it.toString().trim(),
                token
            )
            ToastUtils.success(message)
        }
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}