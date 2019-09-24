package com.diaryofrifat.thecounter.main.ui.app.authentication.resetpassword

import android.content.Context
import android.content.Intent
import android.text.InputType
import android.text.TextUtils
import android.view.View
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.databinding.ActivityResetPasswordBinding
import com.diaryofrifat.thecounter.main.ui.app.authentication.welcome.WelcomeActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseActivity
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.setRipple
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.helper.KeyboardUtils
import com.diaryofrifat.thecounter.utils.helper.ViewUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import java.util.regex.Pattern

class ResetPasswordActivity : BaseActivity<ResetPasswordMvpView, ResetPasswordPresenter>(), ResetPasswordMvpView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context, email: String, token: String) {
            val intent = Intent(context, ResetPasswordActivity::class.java)
            intent.putExtra(Constants.IntentKeys.EMAIL, email)
            intent.putExtra(Constants.IntentKeys.TOKEN, token)
            runCurrentActivity(context, intent)
        }
    }

    private lateinit var mBinding: ActivityResetPasswordBinding
    private var mEmail: String? = null
    private var mToken: String? = null

    override val layoutResourceId: Int
        get() = R.layout.activity_reset_password

    override fun getActivityPresenter(): ResetPasswordPresenter {
        return ResetPasswordPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as ActivityResetPasswordBinding
        initialize()
        extractDataFromIntent()
        setListeners()
    }

    private fun initialize() {
        mBinding.textViewResetPassword.setRipple(R.color.colorWhite50)
        mBinding.imageViewNavigator.setRipple(R.color.colorPrimary50)

        mBinding.textInputLayoutPassword.hint = getString(R.string.reset_password_new_password)
        mBinding.textInputLayoutConfirmPassword.hint = getString(R.string.reset_password_confirm_password)

        val desiredInputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        val desiredTypeface = ViewUtils.getFont(R.font.semi_bold)

        mBinding.editTextPassword.inputType = desiredInputType
        mBinding.editTextPassword.typeface = desiredTypeface

        mBinding.editTextConfirmPassword.inputType = desiredInputType
        mBinding.editTextConfirmPassword.typeface = desiredTypeface
    }

    private fun extractDataFromIntent() {
        if (intent != null) {
            intent.extras?.let {
                if (it.containsKey(Constants.IntentKeys.EMAIL)) {
                    mEmail = it.getString(Constants.IntentKeys.EMAIL)
                }

                if (it.containsKey(Constants.IntentKeys.TOKEN)) {
                    mToken = it.getString(Constants.IntentKeys.TOKEN)
                }
            }
        }
    }

    private fun setListeners() {
        setClickListener(mBinding.imageViewNavigator, mBinding.textViewResetPassword)
    }

    override fun stopUI() {

    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_navigator -> {
                onBackPressed()
            }

            R.id.text_view_reset_password -> {
                goForResettingPassword()
            }
        }
    }

    private fun goForResettingPassword() {
        if (mToken != null
            && mEmail != null
            && mBinding.editTextPassword.text != null
            && mBinding.editTextConfirmPassword.text != null
        ) {
            if (TextUtils.isEmpty(mBinding.editTextPassword.text.toString().trim())
                || TextUtils.isEmpty(mBinding.editTextConfirmPassword.text.toString().trim())
            ) {
                onError(getString(R.string.login_error_valid_fields))
            } else if (mBinding.editTextPassword.text.toString().trim() !=
                mBinding.editTextConfirmPassword.text.toString().trim()
            ) {
                onError(getString(R.string.registration_error_not_same_password))
            } else if (mBinding.editTextPassword.text.toString().trim().length <
                DataUtils.getInteger(R.integer.character_min_limit_password)
            ) {
                onError(getString(R.string.registration_error_valid_password))
            } else if (mBinding.editTextConfirmPassword.text.toString().trim().length <
                DataUtils.getInteger(R.integer.character_min_limit_password)
            ) {
                onError(getString(R.string.registration_error_valid_password))
            } else if (!(Pattern.compile(Constants.Common.REGEX_PASSWORD)
                    .matcher(mBinding.editTextPassword.text.toString().trim())
                    .matches())
            ) {
                onError(getString(R.string.registration_error_proper_password))
            } else {
                KeyboardUtils.hideKeyboard(this)
                presenter.resetPassword(
                    this,
                    mEmail!!,
                    mToken!!,
                    mBinding.editTextPassword.text.toString().trim()
                )
            }
        } else {
            onError(getString(R.string.reset_password_error_could_not_reset))
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
        WelcomeActivity.startActivity(this)
        finish()
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}