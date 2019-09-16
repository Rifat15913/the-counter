package com.itechsoftsolutions.mtcore.main.ui.app.authentication.verification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.databinding.ActivityCodeVerificationBinding
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.ui.app.authentication.resetpassword.ResetPasswordActivity
import com.itechsoftsolutions.mtcore.main.ui.app.landing.container.ContainerActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseActivity
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.setRipple
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.KeyboardUtils
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils

class CodeVerificationActivity : BaseActivity<CodeVerificationMvpView, CodeVerificationPresenter>(),
    CodeVerificationMvpView {

    companion object {
        const val COMING_FROM_FORGOT_PASSWORD = 1
        const val COMING_FROM_LOGIN = 2
        const val COMING_FROM_SETTINGS = 3
        const val COMING_FROM_MTCORE_WALLET = 4

        /**
         * This method starts current activity
         *
         * @param context UI context
         * @param walletAddress wallet address of the receiving user
         * @param walletID wallet id of the sending user
         * @param amount amount to be sent
         * @param note note to be added
         * */
        fun startActivity(
            context: Context,
            walletAddress: String,
            walletID: String,
            amount: String,
            note: String?
        ) {
            val intent = Intent(context, CodeVerificationActivity::class.java)
            intent.putExtra(
                CodeVerificationActivity::class.java.simpleName,
                COMING_FROM_MTCORE_WALLET
            )
            intent.putExtra(Constants.IntentKeys.NOTE, note)
            intent.putExtra(Constants.IntentKeys.WALLET_ID, walletID)
            intent.putExtra(Constants.IntentKeys.WALLET_ADDRESS, walletAddress)
            intent.putExtra(Constants.IntentKeys.AMOUNT, amount)
            runCurrentActivity(context, intent)
        }

        /**
         * This method starts current activity
         *
         * @param context UI context
         * @param email user's email
         * @param token verification token
         * */
        fun startActivity(context: Context, email: String, token: String) {
            val intent = Intent(context, CodeVerificationActivity::class.java)
            intent.putExtra(
                CodeVerificationActivity::class.java.simpleName,
                COMING_FROM_FORGOT_PASSWORD
            )
            intent.putExtra(Constants.IntentKeys.EMAIL, email)
            intent.putExtra(Constants.IntentKeys.TOKEN, token)
            runCurrentActivity(context, intent)
        }

        /**
         * This method starts current activity
         *
         * @param context UI context
         * @param rootCode root code from the this activity constants
         * */
        fun startActivity(context: Context, rootCode: Int) {
            val intent = Intent(context, CodeVerificationActivity::class.java)
            intent.putExtra(CodeVerificationActivity::class.java.simpleName, rootCode)
            runCurrentActivity(context, intent)
        }
    }

    private lateinit var mBinding: ActivityCodeVerificationBinding
    private var mEmail: String? = null
    private var mToken: String? = null
    private var mRootCode: Int? = null
    private var mWalletID: String? = null
    private var mWalletAddress: String? = null
    private var mAmount: String? = null
    private var mNote: String? = null

    override val layoutResourceId: Int
        get() = R.layout.activity_code_verification

    override fun getActivityPresenter(): CodeVerificationPresenter {
        return CodeVerificationPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as ActivityCodeVerificationBinding
        initialize()
        extractDataFromIntent()
        setListeners()
    }

    private fun initialize() {
        mBinding.textViewRequest.setRipple(R.color.colorWhite50)
        mBinding.imageViewNavigator.setRipple(R.color.colorPrimary50)
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

                if (it.containsKey(Constants.IntentKeys.WALLET_ID)) {
                    mWalletID = it.getString(Constants.IntentKeys.WALLET_ID)
                }

                if (it.containsKey(Constants.IntentKeys.WALLET_ADDRESS)) {
                    mWalletAddress = it.getString(Constants.IntentKeys.WALLET_ADDRESS)
                }

                if (it.containsKey(Constants.IntentKeys.AMOUNT)) {
                    mAmount = it.getString(Constants.IntentKeys.AMOUNT)
                }

                if (it.containsKey(Constants.IntentKeys.NOTE)) {
                    mNote = it.getString(Constants.IntentKeys.NOTE)
                }

                if (it.containsKey(CodeVerificationActivity::class.java.simpleName)) {
                    mRootCode = it.getInt(CodeVerificationActivity::class.java.simpleName)
                }
            }
        }
    }

    private fun setListeners() {
        setClickListener(mBinding.imageViewNavigator, mBinding.textViewRequest)
    }

    override fun stopUI() {

    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_navigator -> {
                onBackPressed()
            }

            R.id.text_view_request -> {
                KeyboardUtils.hideKeyboard(this)

                when (mRootCode) {
                    COMING_FROM_FORGOT_PASSWORD -> {
                        mBinding.pinEntryEditTextCodeVerification.text?.let {
                            if (mToken != null && mToken == it.toString().trim() && mEmail != null) {
                                ResetPasswordActivity.startActivity(this, mEmail!!, mToken!!)
                            } else {
                                onError(getString(R.string.code_verification_error_invalid_token))
                            }
                        }
                    }

                    COMING_FROM_SETTINGS -> {
                        mBinding.pinEntryEditTextCodeVerification.text?.let {
                            if (it.length > 5) {
                                presenter.removeTwoFactorAuthentication(
                                    this,
                                    it.toString().trim()
                                )
                            } else {
                                onError(getString(R.string.code_verification_error_invalid_token))
                            }
                        }
                    }

                    COMING_FROM_LOGIN -> {
                        mBinding.pinEntryEditTextCodeVerification.text?.let {
                            if (it.length > 5) {
                                presenter.verifyTwoFactorAuthentication(
                                    this,
                                    it.toString().trim()
                                )
                            } else {
                                onError(getString(R.string.code_verification_error_invalid_token))
                            }
                        }
                    }

                    COMING_FROM_MTCORE_WALLET -> {
                        mBinding.pinEntryEditTextCodeVerification.text?.let {
                            if (it.length > 5
                                && !TextUtils.isEmpty(mAmount)
                                && !TextUtils.isEmpty(mWalletAddress)
                                && !TextUtils.isEmpty(mWalletID)
                            ) {
                                presenter.sendMtcore(
                                    this,
                                    it.toString().trim(),
                                    mWalletAddress!!,
                                    mWalletID!!,
                                    mAmount!!,
                                    mNote
                                )
                            } else {
                                onError(getString(R.string.code_verification_error_invalid_token))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)

        when (mRootCode) {
            COMING_FROM_MTCORE_WALLET -> {
                finish()
            }

            COMING_FROM_FORGOT_PASSWORD -> {
                finish()
            }

            COMING_FROM_SETTINGS -> {
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            COMING_FROM_LOGIN -> {
                ContainerActivity.startActivity(this)
                finish()
            }
        }
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)

        when (mRootCode) {
            COMING_FROM_MTCORE_WALLET -> {
                finish()
            }

            COMING_FROM_FORGOT_PASSWORD -> {
                // Do nothing
            }

            COMING_FROM_SETTINGS -> {
                // Do nothing
            }

            COMING_FROM_LOGIN -> {
                // Do nothing
            }
        }
    }

    override fun onBackPressed() {
        when (mRootCode) {
            COMING_FROM_MTCORE_WALLET -> {
                // Do nothing
            }

            COMING_FROM_FORGOT_PASSWORD -> {
                // Do nothing
            }

            COMING_FROM_SETTINGS -> {
                val intent = Intent()
                setResult(Activity.RESULT_CANCELED, intent)
                finish()
            }

            COMING_FROM_LOGIN -> {
                presenter.compositeDisposable.add(
                    BaseRepository.on().logOut(this, true)
                )
            }
        }

        super.onBackPressed()
    }
}