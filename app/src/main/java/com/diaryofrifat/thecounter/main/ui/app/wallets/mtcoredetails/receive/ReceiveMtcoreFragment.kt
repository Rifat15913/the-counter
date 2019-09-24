package com.diaryofrifat.thecounter.main.ui.app.wallets.mtcoredetails.receive

import android.graphics.Paint
import android.text.TextUtils
import android.view.View
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.google.zxing.BarcodeFormat
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.makeItVisible
import com.diaryofrifat.thecounter.main.ui.base.setRipple
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.ViewUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_receive_mtcore.*

class ReceiveMtcoreFragment : BaseFragment<ReceiveMtcoreMvpView, ReceiveMtcorePresenter>(),
    ReceiveMtcoreMvpView {

    override val layoutId: Int
        get() = R.layout.fragment_receive_mtcore

    override fun getFragmentPresenter(): ReceiveMtcorePresenter {
        return ReceiveMtcorePresenter()
    }

    override fun startUI() {
        extractDataFromArguments()
        initialize()
        setListeners()
        loadData()
    }

    private fun setListeners() {
        setClickListener(text_view_generate_new_address, image_view_copy_address)
    }

    private fun loadData() {
        presenter.walletID?.let {
            presenter.getMtcoreWalletReceivingAddress(mContext!!, it)
        }
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
        text_view_generate_new_address.paintFlags =
            text_view_generate_new_address.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        image_view_copy_address.setRipple(R.color.colorWhite)
    }

    override fun stopUI() {

    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onSuccess(address: String) {
        ProgressDialogUtils.on().hideProgressDialog()

        if (!TextUtils.isEmpty(address)) {
            text_view_address.text = address
            image_view_copy_address.makeItVisible()

            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(
                address,
                BarcodeFormat.QR_CODE, 1000, 1000
            )

            bitmap?.let {
                val roundedDrawable = RoundedBitmapDrawableFactory.create(
                    ViewUtils.getResources(), it
                )
                roundedDrawable.cornerRadius = ViewUtils.dpToPx(15)
                image_view_qr_code.setImageDrawable(roundedDrawable)
            }
        }
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.text_view_generate_new_address -> {
                presenter.walletID?.let {
                    presenter.generateMtcoreWalletReceivingAddress(mContext!!, it)
                }
            }

            R.id.image_view_copy_address -> {
                presenter.walletAddress?.let {
                    presenter.copyTextToClipboard(mContext!!, it)
                }
            }
        }
    }
}