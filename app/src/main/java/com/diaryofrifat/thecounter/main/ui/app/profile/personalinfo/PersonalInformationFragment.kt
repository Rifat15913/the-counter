package com.diaryofrifat.thecounter.main.ui.app.profile.personalinfo

import android.app.DatePickerDialog
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserEntity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.TimeUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_basic_information.text_view_save_changes
import kotlinx.android.synthetic.main.fragment_personal_information.*
import java.util.*


class PersonalInformationFragment :
    BaseFragment<PersonalInformationMvpView, PersonalInformationPresenter>(),
    PersonalInformationMvpView {

    private var mSpinnerIAmAdapter: ArrayAdapter<CharSequence>? = null
    private var mSpinnerGenderAdapter: ArrayAdapter<CharSequence>? = null
    private var mSpinnerMaritalStateAdapter: ArrayAdapter<CharSequence>? = null
    private var mSpinnerCountryAdapter: ArrayAdapter<CharSequence>? = null

    override val layoutId: Int
        get() = R.layout.fragment_personal_information

    override fun getFragmentPresenter(): PersonalInformationPresenter {
        return PersonalInformationPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
        loadData()
    }

    private fun initialize() {
        mSpinnerIAmAdapter = ArrayAdapter.createFromResource(
            mContext!!,
            R.array.i_am,
            R.layout.spinner_custom_item6
        ).apply {
            this.setDropDownViewResource(R.layout.spinner_custom_dark_item6)
            spinner_i_am?.adapter = this
        }

        mSpinnerGenderAdapter = ArrayAdapter.createFromResource(
            mContext!!,
            R.array.gender,
            R.layout.spinner_custom_item6
        ).apply {
            this.setDropDownViewResource(R.layout.spinner_custom_dark_item6)
            spinner_gender?.adapter = this
        }

        mSpinnerMaritalStateAdapter = ArrayAdapter.createFromResource(
            mContext!!,
            R.array.marital_state,
            R.layout.spinner_custom_item6
        ).apply {
            this.setDropDownViewResource(R.layout.spinner_custom_dark_item6)
            spinner_marital_state?.adapter = this
        }
    }

    private fun setListeners() {
        setClickListener(text_view_save_changes, text_view_date_of_birth)
    }

    private fun loadData() {
        presenter.getUserFromCloud(mContext!!)
        presenter.getUserFromDatabase()
        presenter.getCountryList()
    }

    override fun stopUI() {

    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.text_view_save_changes -> {
                if (TextUtils.isEmpty(edit_text_phone_number.text.toString().trim())
                    || Patterns.PHONE.matcher(edit_text_phone_number.text.toString()).matches()
                ) {
                    presenter.updatePersonalInformation(
                        mContext!!,
                        if (!TextUtils.isEmpty(edit_text_phone_number.text.toString().trim())) {
                            edit_text_phone_number.text.toString().trim()
                        } else {
                            null
                        },
                        if (!TextUtils.isEmpty(edit_text_vat_id.text.toString().trim())) {
                            edit_text_vat_id.text.toString().trim()
                        } else {
                            null
                        },
                        if (!TextUtils.isEmpty(edit_text_id_or_passport_number.text.toString().trim())) {
                            edit_text_id_or_passport_number.text.toString().trim()
                        } else {
                            null
                        },
                        spinner_i_am?.selectedItem.toString().trim(),
                        spinner_gender?.selectedItem.toString().trim(),
                        spinner_marital_state?.selectedItem.toString().trim(),
                        if (presenter.countryList.isEmpty()) {
                            if (presenter.currentUser != null
                                && !TextUtils.isEmpty(presenter.currentUser?.fiscalCountry)
                            ) {
                                spinner_fiscal_country?.selectedItem.toString().trim()
                            } else {
                                null
                            }
                        } else {
                            spinner_fiscal_country?.selectedItem.toString().trim()
                        },
                        if (!TextUtils.isEmpty(text_view_date_of_birth.text.toString().trim())) {
                            text_view_date_of_birth.text.toString().trim()
                        } else {
                            null
                        }
                    )
                } else {
                    onError(getString(R.string.personal_info_error_phone_number))
                }
            }

            R.id.text_view_date_of_birth -> {
                val calendar = if (!TextUtils.isEmpty(text_view_date_of_birth.text)
                ) {
                    TimeUtils.getCalendarFromDate(
                        text_view_date_of_birth.text.toString().trim(),
                        Constants.Common.APP_PROFILE_DATE_FORMAT
                    )
                } else {
                    Calendar.getInstance()
                }

                val listener =
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        val modifiedCalendar = Calendar.getInstance()
                        modifiedCalendar.set(Calendar.YEAR, year)
                        modifiedCalendar.set(Calendar.MONTH, monthOfYear)
                        modifiedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        text_view_date_of_birth.text =
                            TimeUtils.getFormattedDateTime(
                                modifiedCalendar.timeInMillis,
                                Constants.Common.APP_PROFILE_DATE_FORMAT
                            )
                    }

                val dialog = DatePickerDialog(
                    mContext!!, listener,
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                )

                dialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
                dialog.show()
            }
        }
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
    }

    override fun onGettingUser(user: UserEntity) {

        if (!TextUtils.isEmpty(user.iAm)) {
            mSpinnerIAmAdapter?.let {
                for (i in 0 until it.count) {
                    if (user.iAm == it.getItem(i)) {
                        spinner_i_am?.setSelection(i)
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(user.fiscalCountry)) {
            mSpinnerCountryAdapter?.let {
                for (i in 0 until it.count) {
                    if (user.fiscalCountry == it.getItem(i)) {
                        spinner_fiscal_country?.setSelection(i)
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(user.gender)) {
            mSpinnerGenderAdapter?.let {
                for (i in 0 until it.count) {
                    if (user.gender == it.getItem(i)) {
                        spinner_gender?.setSelection(i)
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(user.maritalStatus)) {
            mSpinnerMaritalStateAdapter?.let {
                for (i in 0 until it.count) {
                    if (user.maritalStatus == it.getItem(i)) {
                        spinner_marital_state?.setSelection(i)
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(user.dateOfBirth)) {
            text_view_date_of_birth.text =
                TimeUtils.getFormattedDateTime(
                    TimeUtils.getCalendarFromDate(user.dateOfBirth!!).timeInMillis,
                    Constants.Common.APP_PROFILE_DATE_FORMAT
                )
        }

        if (!TextUtils.isEmpty(user.idOrPassportNumber)) {
            edit_text_id_or_passport_number.setText(user.idOrPassportNumber)
        }

        if (!TextUtils.isEmpty(user.vatID)) {
            edit_text_vat_id.setText(user.vatID)
        }

        if (!TextUtils.isEmpty(user.phoneNumber)) {
            edit_text_phone_number.setText(user.phoneNumber)
        }
    }

    override fun onGettingCountryList(list: List<String>) {
        mSpinnerCountryAdapter = ArrayAdapter<CharSequence>(
            mContext!!,
            R.layout.spinner_custom_item6,
            list
        ).apply {
            this.setDropDownViewResource(R.layout.spinner_custom_dark_item6)
            spinner_fiscal_country?.adapter = this
        }
    }
}