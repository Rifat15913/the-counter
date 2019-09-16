package com.itechsoftsolutions.mtcore.main.ui.app.authentication.registration

import android.content.Context
import android.content.Intent
import android.text.InputType
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.databinding.ActivityRegistrationBinding
import com.itechsoftsolutions.mtcore.main.ui.app.authentication.login.LoginActivity
import com.itechsoftsolutions.mtcore.main.ui.app.authentication.welcome.WelcomeActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseActivity
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.makeItInvisible
import com.itechsoftsolutions.mtcore.main.ui.base.makeItVisible
import com.itechsoftsolutions.mtcore.main.ui.base.setRipple
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.KeyboardUtils
import com.itechsoftsolutions.mtcore.utils.helper.ViewUtils
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_registration.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class RegistrationActivity : BaseActivity<RegistrationMvpView, RegistrationPresenter>(),
    RegistrationMvpView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            runCurrentActivity(context, Intent(context, RegistrationActivity::class.java))
        }
    }

    private lateinit var mBinding: ActivityRegistrationBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_registration

    override fun getActivityPresenter(): RegistrationPresenter {
        return RegistrationPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as ActivityRegistrationBinding
        initialize()
        setListeners()
        loadData()
    }

    private fun loadData() {
        presenter.getCountryList()
    }

    private fun initialize() {
        val desiredInputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        mBinding.editTextPassword.inputType = desiredInputType
        mBinding.editTextPassword.typeface = mBinding.editTextEmail.typeface
        mBinding.editTextConfirmPassword.inputType = desiredInputType
        mBinding.editTextConfirmPassword.typeface = mBinding.editTextEmail.typeface

        mBinding.textInputLayoutFirstName.hint = getString(R.string.registration_first_name)
        mBinding.textInputLayoutLastName.hint = getString(R.string.registration_last_name)
        mBinding.textInputLayoutEmail.hint = getString(R.string.registration_email)
        mBinding.textInputLayoutPassword.hint = getString(R.string.registration_password)
        mBinding.textInputLayoutConfirmPassword.hint = getString(R.string.registration_confirm_password)

        mBinding.textViewRegister.setRipple(R.color.colorWhite50)
        mBinding.constraintLayoutHaveAccountContainer.setRipple(R.color.colorPrimary50)

        mBinding.viewColorWeak.setBackgroundColor(ViewUtils.getColor(R.color.colorWeak))
        mBinding.viewColorNormal.setBackgroundColor(ViewUtils.getColor(R.color.colorNormal))
        mBinding.viewColorStrong.setBackgroundColor(ViewUtils.getColor(R.color.colorStrong))

        presenter.compositeDisposable.add(RxTextView.afterTextChangeEvents(mBinding.editTextConfirmPassword)
            .map {
                it.view().length()
            }
            .debounce(DataUtils.getInteger(R.integer.debounce_time_out).toLong(), TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .toFlowable(BackpressureStrategy.LATEST)
            .subscribe({
                when {
                    it <= DataUtils.getInteger(R.integer.blank_password) -> hideAllBars()
                    it < DataUtils.getInteger(R.integer.weak_password) -> showOnlyWeakBar()
                    it < DataUtils.getInteger(R.integer.normal_password) -> showOnlyNormalBar()
                    else -> showOnlyStrongBar()
                }
            }, {
                Timber.e(it)
            })
        )
    }

    private fun setListeners() {
        setClickListener(mBinding.constraintLayoutHaveAccountContainer, mBinding.textViewRegister)
    }

    private fun hideAllBars() {
        mBinding.viewColorWeak.makeItInvisible()
        mBinding.textViewWeak.makeItInvisible()
        mBinding.viewColorNormal.makeItInvisible()
        mBinding.textViewNormal.makeItInvisible()
        mBinding.viewColorStrong.makeItInvisible()
        mBinding.textViewStrong.makeItInvisible()
    }

    private fun showOnlyWeakBar() {
        mBinding.viewColorWeak.makeItVisible()
        mBinding.textViewWeak.makeItVisible()
        mBinding.viewColorNormal.makeItInvisible()
        mBinding.textViewNormal.makeItInvisible()
        mBinding.viewColorStrong.makeItInvisible()
        mBinding.textViewStrong.makeItInvisible()
    }

    private fun showOnlyNormalBar() {
        mBinding.viewColorWeak.makeItInvisible()
        mBinding.textViewWeak.makeItInvisible()
        mBinding.viewColorNormal.makeItVisible()
        mBinding.textViewNormal.makeItVisible()
        mBinding.viewColorStrong.makeItInvisible()
        mBinding.textViewStrong.makeItInvisible()
    }

    private fun showOnlyStrongBar() {
        mBinding.viewColorWeak.makeItInvisible()
        mBinding.textViewWeak.makeItInvisible()
        mBinding.viewColorNormal.makeItInvisible()
        mBinding.textViewNormal.makeItInvisible()
        mBinding.viewColorStrong.makeItVisible()
        mBinding.textViewStrong.makeItVisible()
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.constraint_layout_have_account_container -> {
                LoginActivity.startActivity(this)
                finish()
            }

            R.id.image_view_navigator -> {
                onBackPressed()
            }

            R.id.text_view_register -> {
                goForRegistration()
            }
        }
    }

    private fun goForRegistration() {
        if (TextUtils.isEmpty(mBinding.editTextFirstName.text.toString().trim())
            || TextUtils.isEmpty(mBinding.editTextLastName.text.toString().trim())
            || TextUtils.isEmpty(mBinding.editTextEmail.text.toString().trim())
            || TextUtils.isEmpty(mBinding.editTextPassword.text.toString().trim())
            || TextUtils.isEmpty(mBinding.editTextConfirmPassword.text.toString().trim())
        ) {
            showAlert(getString(R.string.registration_error_valid_fields))
        } else {
            if (!(Patterns.EMAIL_ADDRESS.matcher(mBinding.editTextEmail.text.toString().trim()).matches())) {
                showAlert(getString(R.string.registration_error_valid_email))
            } else if (mBinding.editTextPassword.text.toString().trim() !=
                mBinding.editTextConfirmPassword.text.toString().trim()
            ) {
                showAlert(getString(R.string.registration_error_not_same_password))
            } else if (mBinding.editTextPassword.text.toString().trim().length <
                DataUtils.getInteger(R.integer.character_min_limit_password)
            ) {
                showAlert(getString(R.string.registration_error_valid_password))
            } else if (mBinding.editTextConfirmPassword.text.toString().trim().length <
                DataUtils.getInteger(R.integer.character_min_limit_password)
            ) {
                showAlert(getString(R.string.registration_error_valid_password))
            } else if (!(Pattern.compile(Constants.Common.REGEX_PASSWORD)
                    .matcher(mBinding.editTextPassword.text.toString().trim())
                    .matches())
            ) {
                showAlert(getString(R.string.registration_error_proper_password))
            } else {
                KeyboardUtils.hideKeyboard(this)
                presenter.register(
                    this,
                    mBinding.editTextFirstName.text.toString().trim(),
                    mBinding.editTextLastName.text.toString().trim(),
                    mBinding.editTextEmail.text.toString().trim(),
                    mBinding.editTextPassword.text.toString().trim(),
                    spinner_bank_country.selectedItem.toString()
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
        ToastUtils.success(message, true)
        WelcomeActivity.startActivity(this)
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onGettingCountryList(list: List<String>) {
        val spinnerAdapter = ArrayAdapter(
            this,
            R.layout.spinner_custom_item5,
            list
        )

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_custom_dark_item5)
        spinner_bank_country?.adapter = spinnerAdapter
    }
}