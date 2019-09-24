package com.diaryofrifat.thecounter.main.ui.app.wallets.mtcoredetails.send

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.google.zxing.integration.android.IntentIntegrator
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.app.authentication.verification.CodeVerificationActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.setRipple
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_send_mtcore.*


class SendMtcoreFragment : BaseFragment<SendMtcoreMvpView, SendMtcorePresenter>(),
    SendMtcoreMvpView {

    private var mQRCodeScanner: IntentIntegrator? = null

    override val layoutId: Int
        get() = R.layout.fragment_send_mtcore

    override fun getFragmentPresenter(): SendMtcorePresenter {
        return SendMtcorePresenter()
    }

    override fun startUI() {
        extractDataFromArguments()
        initialize()
        setListeners()
        loadData()
    }

    private fun extractDataFromArguments() {
        val bundle = arguments

        if (bundle != null) {
            if (bundle.containsKey(Constants.IntentKeys.WALLET_ID)) {
                presenter.walletID = bundle.getString(Constants.IntentKeys.WALLET_ID)
            }
        }
    }

    private fun initialize() {
        text_input_layout_address.hint = getString(R.string.mtcore_wallet_details_to_address)
        text_input_layout_amount.hint = getString(R.string.mtcore_wallet_details_amount)
        text_input_layout_note.hint = getString(R.string.mtcore_wallet_details_note)

        text_view_send.setRipple(R.color.colorWhite26)

        mQRCodeScanner = IntentIntegrator.forSupportFragment(this)
        mQRCodeScanner?.setOrientationLocked(false)
        mQRCodeScanner?.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        mQRCodeScanner?.setCameraId(0)
        mQRCodeScanner?.setBeepEnabled(true)
    }

    private fun setListeners() {
        setClickListener(text_view_send, image_view_scan)
    }

    private fun loadData() {
        presenter.walletID?.let {

        }
    }

    override fun stopUI() {

    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onSuccess(
        walletAddress: String,
        walletID: String,
        amount: String,
        note: String?
    ) {
        ProgressDialogUtils.on().hideProgressDialog()

        edit_text_amount.text?.clear()
        edit_text_address.text?.clear()
        edit_text_note.text?.clear()

        CodeVerificationActivity.startActivity(mContext!!, walletAddress, walletID, amount, note)
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.text_view_send -> {
                if (!TextUtils.isEmpty(presenter.walletID)) {
                    if (!TextUtils.isEmpty(edit_text_address.text)
                        && !TextUtils.isEmpty(edit_text_amount.text)
                    ) {
                        presenter.validateUserWallet(
                            mContext!!,
                            edit_text_address.text.toString().trim(),
                            presenter.walletID!!,
                            edit_text_amount.text.toString().trim(),
                            edit_text_note.text.toString().trim()
                        )
                    } else {
                        onError(getString(R.string.mtcore_wallet_error_fields_are_required))
                    }
                } else {
                    onError(getString(R.string.error_something_went_wrong))
                }
            }

            R.id.image_view_scan -> {
                mQRCodeScanner?.initiateScan()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                edit_text_address.setText(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}