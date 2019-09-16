package com.itechsoftsolutions.mtcore.main.ui.app.settings.password

import android.content.Context
import android.content.Intent
import android.text.InputType
import android.text.TextUtils
import android.view.View
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseActivity
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.setRipple
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.KeyboardUtils
import com.itechsoftsolutions.mtcore.utils.helper.ViewUtils
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.activity_change_password.*
import java.util.regex.Pattern

class ChangePasswordActivity : BaseActivity<ChangePasswordMvpView, ChangePasswordPresenter>(),
    ChangePasswordMvpView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            runCurrentActivity(context, Intent(context, ChangePasswordActivity::class.java))
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_change_password

    override fun getActivityPresenter(): ChangePasswordPresenter {
        return ChangePasswordPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
    }

    private fun initialize() {
        text_view_change_password.setRipple(R.color.colorWhite50)
        image_view_navigator.setRipple(R.color.colorPrimary50)

        text_input_layout_old_password.hint =
            getString(R.string.change_password_old_password)
        text_input_layout_new_password.hint =
            getString(R.string.change_password_new_password)
        text_input_layout_confirm_password.hint =
            getString(R.string.change_password_confirm_password)

        val desiredInputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        val desiredTypeface = ViewUtils.getFont(R.font.medium)

        edit_text_old_password.inputType = desiredInputType
        edit_text_old_password.typeface = desiredTypeface

        edit_text_new_password.inputType = desiredInputType
        edit_text_new_password.typeface = desiredTypeface

        edit_text_confirm_password.inputType = desiredInputType
        edit_text_confirm_password.typeface = desiredTypeface
    }

    private fun setListeners() {
        setClickListener(image_view_navigator, text_view_change_password)
    }

    override fun stopUI() {

    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_navigator -> {
                onBackPressed()
            }

            R.id.text_view_change_password -> {
                goForChangingPassword()
            }
        }
    }

    private fun goForChangingPassword() {
        if (edit_text_old_password.text != null
            && edit_text_new_password.text != null
            && edit_text_confirm_password.text != null
        ) {
            if (TextUtils.isEmpty(edit_text_old_password.text.toString().trim())
                || TextUtils.isEmpty(edit_text_new_password.text.toString().trim())
                || TextUtils.isEmpty(edit_text_confirm_password.text.toString().trim())
            ) {
                onError(getString(R.string.login_error_valid_fields))
            } else if (edit_text_new_password.text.toString().trim() !=
                edit_text_confirm_password.text.toString().trim()
            ) {
                onError(getString(R.string.change_password_new_passwords_dont_match))
            } else if (edit_text_old_password.text.toString().trim().length <
                DataUtils.getInteger(R.integer.character_min_limit_password)
            ) {
                onError(getString(R.string.registration_error_valid_password))
            } else if (edit_text_new_password.text.toString().trim().length <
                DataUtils.getInteger(R.integer.character_min_limit_password)
            ) {
                onError(getString(R.string.registration_error_valid_password))
            } else if (edit_text_confirm_password.text.toString().trim().length <
                DataUtils.getInteger(R.integer.character_min_limit_password)
            ) {
                onError(getString(R.string.registration_error_valid_password))
            } else if (!(Pattern.compile(Constants.Common.REGEX_PASSWORD)
                    .matcher(edit_text_old_password.text.toString().trim())
                    .matches())
            ) {
                onError(getString(R.string.registration_error_proper_password))
            } else if (!(Pattern.compile(Constants.Common.REGEX_PASSWORD)
                    .matcher(edit_text_new_password.text.toString().trim())
                    .matches())
            ) {
                onError(getString(R.string.registration_error_proper_password))
            } else if (!(Pattern.compile(Constants.Common.REGEX_PASSWORD)
                    .matcher(edit_text_confirm_password.text.toString().trim())
                    .matches())
            ) {
                onError(getString(R.string.registration_error_proper_password))
            } else {
                KeyboardUtils.hideKeyboard(this)
                presenter.changePassword(
                    this,
                    edit_text_old_password.text.toString().trim(),
                    edit_text_new_password.text.toString().trim(),
                    edit_text_confirm_password.text.toString().trim()
                )
            }
        } else {
            onError(getString(R.string.change_password_error_could_not_change))
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
        finish()
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}