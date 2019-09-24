package com.diaryofrifat.thecounter.main.ui.app.profile.address

import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserEntity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_address.*
import kotlinx.android.synthetic.main.fragment_basic_information.text_view_save_changes

class AddressFragment : BaseFragment<AddressMvpView, AddressPresenter>(),
    AddressMvpView {

    private var mSpinnerCountryAdapter: ArrayAdapter<CharSequence>? = null

    override val layoutId: Int
        get() = R.layout.fragment_address

    override fun getFragmentPresenter(): AddressPresenter {
        return AddressPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
        loadData()
    }

    private fun initialize() {

    }

    private fun setListeners() {
        setClickListener(text_view_save_changes)
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
                presenter.updateAddress(
                    mContext!!,
                    if (presenter.countryList.isEmpty()) {
                        if (presenter.currentUser != null
                            && !TextUtils.isEmpty(presenter.currentUser?.country)
                        ) {
                            spinner_country?.selectedItem.toString().trim()
                        } else {
                            null
                        }
                    } else {
                        spinner_country?.selectedItem.toString().trim()
                    },
                    if (!TextUtils.isEmpty(edit_text_state.text.toString().trim())) {
                        edit_text_state.text.toString().trim()
                    } else {
                        null
                    },
                    if (!TextUtils.isEmpty(edit_text_zip_code.text.toString().trim())) {
                        edit_text_zip_code.text.toString().trim()
                    } else {
                        null
                    },
                    if (!TextUtils.isEmpty(edit_text_city.text.toString().trim())) {
                        edit_text_city.text.toString().trim()
                    } else {
                        null
                    },
                    null,
                    if (!TextUtils.isEmpty(edit_text_address.text.toString().trim())) {
                        edit_text_address.text.toString().trim()
                    } else {
                        null
                    },
                    if (!TextUtils.isEmpty(edit_text_number.text.toString().trim())) {
                        edit_text_number.text.toString().trim()
                    } else {
                        null
                    },
                    if (!TextUtils.isEmpty(edit_text_complement.text.toString().trim())) {
                        edit_text_complement.text.toString().trim()
                    } else {
                        null
                    }
                )
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
        if (!TextUtils.isEmpty(user.country)
            && presenter.countryList.isNotEmpty()
        ) {
            mSpinnerCountryAdapter = ArrayAdapter<CharSequence>(
                mContext!!,
                R.layout.spinner_custom_item6,
                presenter.countryList as List<CharSequence>
            ).apply {
                this.setDropDownViewResource(R.layout.spinner_custom_dark_item6)
                spinner_country?.adapter = this
            }

            mSpinnerCountryAdapter?.let {
                for (i in 0 until it.count) {
                    if (user.country == it.getItem(i)) {
                        spinner_country?.setSelection(i)
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(user.state)) {
            edit_text_state.setText(user.state)
        }

        if (!TextUtils.isEmpty(user.zipCode)) {
            edit_text_zip_code.setText(user.zipCode)
        }

        if (!TextUtils.isEmpty(user.city)) {
            edit_text_city.setText(user.city)
        }

        if (!TextUtils.isEmpty(user.fullAddress)) {
            edit_text_address.setText(user.fullAddress)
        }

        if (!TextUtils.isEmpty(user.number)) {
            edit_text_number.setText(user.number)
        }

        if (!TextUtils.isEmpty(user.complement)) {
            edit_text_complement.setText(user.complement)
        }
    }

    override fun onGettingCountryList(list: List<String>) {
        mSpinnerCountryAdapter = ArrayAdapter<CharSequence>(
            mContext!!,
            R.layout.spinner_custom_item6,
            list
        ).apply {
            this.setDropDownViewResource(R.layout.spinner_custom_dark_item6)
            spinner_country?.adapter = this
        }

        if (presenter.currentUser != null
            && !TextUtils.isEmpty(presenter.currentUser?.country)
        ) {
            mSpinnerCountryAdapter?.let {
                for (i in 0 until it.count) {
                    if (presenter.currentUser?.country == it.getItem(i)) {
                        spinner_country?.setSelection(i)
                    }
                }
            }
        }
    }
}