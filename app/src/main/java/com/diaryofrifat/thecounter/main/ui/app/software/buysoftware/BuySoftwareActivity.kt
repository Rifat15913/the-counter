package com.diaryofrifat.thecounter.main.ui.app.software.buysoftware

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareStatusEntity
import com.diaryofrifat.thecounter.main.data.remote.model.Bank
import com.diaryofrifat.thecounter.main.data.remote.model.FeesAndVat
import com.diaryofrifat.thecounter.main.ui.base.component.BaseActivity
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.makeItGone
import com.diaryofrifat.thecounter.main.ui.base.makeItVisible
import com.diaryofrifat.thecounter.main.ui.base.setRipple
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.activity_buy_software.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class BuySoftwareActivity : BaseActivity<BuySoftwareMvpView, BuySoftwarePresenter>(),
    BuySoftwareMvpView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * @param software desired software
         * */
        fun startActivity(context: Context, software: SoftwareStatusEntity) {
            val intent = Intent(context, BuySoftwareActivity::class.java)
            intent.putExtra(BuySoftwareActivity::class.java.simpleName, software)
            runCurrentActivity(context, intent)
        }
    }

    private var mCurrentSoftware: SoftwareStatusEntity? = null
    private var mBankList: List<Bank> = ArrayList()
    private var mReferenceID: String? = null

    override val layoutResourceId: Int
        get() = R.layout.activity_buy_software

    override fun getActivityPresenter(): BuySoftwarePresenter {
        return BuySoftwarePresenter()
    }

    override fun startUI() {
        extractDataFromIntent()
        initialize()
        setListeners()
        loadData()
    }

    private fun extractDataFromIntent() {
        if (intent != null) {
            val bundle = intent.extras

            if (bundle != null && bundle.containsKey(BuySoftwareActivity::class.java.simpleName)) {
                mCurrentSoftware = bundle.getParcelable(BuySoftwareActivity::class.java.simpleName)
            }
        }
    }

    private fun initialize() {
        mCurrentSoftware?.let {
            text_view_title.text = it.title
        }

        image_view_navigator.setRipple(R.color.colorPrimary50)
        text_view_order.setRipple(R.color.colorWhite50)

        activateCardPaymentMethod()
    }

    private fun setListeners() {
        setClickListener(
            image_view_navigator,
            text_view_card,
            text_view_bank,
            text_view_order
        )
    }

    private fun loadData() {
        if (mCurrentSoftware != null) {
            presenter.getSoftwareLicenseDetails(this, mCurrentSoftware?.softwareID!!)
        }
    }

    override fun stopUI() {

    }

    override fun onGettingSoftwareLicenseDetails(
        referenceID: String,
        bankList: List<Bank>,
        feesAndVat: FeesAndVat
    ) {
        mReferenceID = referenceID
        mBankList = bankList

        text_view_software_price.text =
            getString(R.string.buy_software_software_price)
        text_view_software_price_amount.text =
            Currency.getInstance(Locale.GERMANY)
                .symbol.plus(
                mCurrentSoftware?.pricePerYear?.toDouble().toString()
            )

        text_view_fees.text =
            String.format(
                Locale.getDefault(),
                getString(R.string.buy_software_fees),
                feesAndVat.fees.toString()
            )
        text_view_fees_amount.text =
            Currency.getInstance(Locale.GERMANY)
                .symbol.plus(
                feesAndVat.yearlyPriceFees.toString()
            )

        text_view_subtotal.text =
            getString(R.string.buy_software_sub_total)
        text_view_subtotal_amount.text =
            Currency.getInstance(Locale.GERMANY)
                .symbol.plus(
                (mCurrentSoftware?.pricePerYear?.toDouble()
                    ?.plus(feesAndVat.yearlyPriceFees))
                    .toString()
            )

        text_view_vat.text =
            String.format(
                Locale.getDefault(),
                getString(R.string.buy_software_vat),
                feesAndVat.vat.toString()
            )
        text_view_vat_amount.text =
            Currency.getInstance(Locale.GERMANY)
                .symbol
                .plus(feesAndVat.yearlyPriceVat.toString())

        text_view_total.text =
            getString(R.string.buy_software_total)
        text_view_total_amount.text =
            Currency.getInstance(Locale.GERMANY)
                .symbol
                .plus(feesAndVat.yearlyTotalPrice.toString())

        val bankNameList: MutableList<String> = ArrayList()
        bankList.forEach {
            bankNameList.add(it.bankName)
        }

        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_custom_item3,
            bankNameList
        )

        adapter.setDropDownViewResource(R.layout.spinner_custom_dark_item3)
        spinner_bank.adapter = adapter

        if (bankNameList.isNotEmpty()) {
            spinner_bank.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val item = bankList[position]

                        text_view_bank_address_content.text = item.bankAddress
                        text_view_bank_country_content.text = item.bankCountry
                        text_view_bank_swift_code_content.text = item.bankSwiftCode
                        text_view_holder_name_content.text = item.holderName
                        text_view_holder_address_content.text = item.holderAddress
                        text_view_holder_iban_content.text = item.holderIBAN
                    }
                }
        }
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onPlacingOrder(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
        onBackPressed()
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_navigator -> {
                onBackPressed()
            }

            R.id.text_view_card -> {
                activateCardPaymentMethod()
            }

            R.id.text_view_bank -> {
                activateBankPaymentMethod()
            }

            R.id.text_view_order -> {
                val selectedBankPosition = spinner_bank.selectedItemPosition
                Timber.d(selectedBankPosition.toString())

                if (mCurrentSoftware != null && mBankList.isNotEmpty()
                    && selectedBankPosition != Constants.Invalid.INVALID_INTEGER
                ) {
                    if (text_view_card.isActivated) {
                        // Do nothing
                    } else {
                        presenter.requestBankSubscription(
                            this,
                            mCurrentSoftware?.softwareID!!,
                            mReferenceID!!,
                            getString(R.string.buy_software_bank).toLowerCase(Locale.getDefault()),
                            mCurrentSoftware?.pricePerYear.toString(),
                            getString(R.string.buy_software_interval_yearly),
                            mBankList[selectedBankPosition].bankID
                        )
                    }
                } else {
                    onError(getString(R.string.buy_software_error_could_not_place_order))
                }
            }
        }
    }

    private fun activateCardPaymentMethod() {
        text_view_card.isActivated = true
        text_view_bank.isActivated = false
        constraint_layout_bank_body_container.makeItGone()
    }

    private fun activateBankPaymentMethod() {
        text_view_card.isActivated = false
        text_view_bank.isActivated = true

        if (mBankList.isEmpty()) {
            ToastUtils.warning(getString(R.string.buy_software_error_no_bank_found))
            constraint_layout_bank_body_container.makeItGone()
        } else {
            constraint_layout_bank_body_container.makeItVisible()
        }
    }
}