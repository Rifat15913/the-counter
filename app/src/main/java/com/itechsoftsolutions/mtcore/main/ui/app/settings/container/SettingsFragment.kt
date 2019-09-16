package com.itechsoftsolutions.mtcore.main.ui.app.settings.container

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.ListPopupWindow
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.app.authentication.verification.CodeVerificationActivity
import com.itechsoftsolutions.mtcore.main.ui.app.landing.container.ContainerActivity
import com.itechsoftsolutions.mtcore.main.ui.app.settings.password.ChangePasswordActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.toTitleCase
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.SharedPrefUtils
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_settings.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class SettingsFragment : BaseFragment<SettingsMvpView, SettingsPresenter>(),
    SettingsMvpView {

    companion object {
        const val REQUEST_CODE_FOR_TOKEN_VERIFICATION = 1
    }

    override val layoutId: Int
        get() = R.layout.fragment_settings

    override fun getFragmentPresenter(): SettingsPresenter {
        return SettingsPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
        loadData()
    }

    private fun loadData() {
        presenter.getSettings(mContext!!)
    }

    private fun initialize() {
        (activity as ContainerActivity).setPageTitle(getString(R.string.settings_title).toTitleCase())
        switch_compat_2_factor_authentication.isChecked =
            SharedPrefUtils.readBoolean(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON)
    }

    private fun setListeners() {
        setClickListener(
            text_view_help_and_support,
            text_view_change_password,
            text_view_2_factor_authentication,
            text_view_language
        )

        presenter.compositeDisposable.add(
            RxCompoundButton.checkedChanges(switch_compat_2_factor_authentication)
                .skip(1)
                .debounce(
                    DataUtils.getInteger(R.integer.debounce_time_out).toLong(),
                    TimeUnit.MILLISECONDS
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .toFlowable(BackpressureStrategy.LATEST)
                .subscribe({
                    if (it) {
                        if (SharedPrefUtils.readBoolean(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET)) {
                            if (SharedPrefUtils.readBoolean(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON)) {
                                // Do nothing
                            } else {
                                // Set the Google Authentication
                                presenter.setTwoFactorAuthentication(mContext!!)
                            }
                        } else {
                            // Toast message
                            ToastUtils.warning(getString(R.string.settings_2_factor_authentication_is_not_set))
                            switch_compat_2_factor_authentication.isChecked = false
                        }
                    } else {
                        if (SharedPrefUtils.readBoolean(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET)) {
                            if (SharedPrefUtils.readBoolean(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON)) {
                                // Start code verification
                                val intent =
                                    Intent(mContext!!, CodeVerificationActivity::class.java)
                                intent.putExtra(
                                    CodeVerificationActivity::class.java.simpleName,
                                    CodeVerificationActivity.COMING_FROM_SETTINGS
                                )
                                startActivityForResult(intent, REQUEST_CODE_FOR_TOKEN_VERIFICATION)
                            } else {
                                // Do nothing
                            }
                        } else {
                            // Do nothing
                        }
                    }

                }, {
                    Timber.e(it)
                })
        )
    }

    override fun stopUI() {

    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.text_view_help_and_support -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(getString(R.string.url_help_and_support))
                startActivity(intent)
            }

            R.id.text_view_change_password -> {
                ChangePasswordActivity.startActivity(mContext!!)
            }

            R.id.text_view_2_factor_authentication -> {
                switch_compat_2_factor_authentication.isChecked =
                    !switch_compat_2_factor_authentication.isChecked
            }

            R.id.text_view_language -> {
                val languageCodeList: MutableList<String> = ArrayList()

                for (i in presenter.languageList.indices) {
                    val language = presenter.languageList[i]
                    languageCodeList.add(language.language)
                }

                val adapter = ArrayAdapter(
                    mContext!!,
                    R.layout.spinner_custom_dark_item6,
                    languageCodeList
                )

                adapter.setDropDownViewResource(R.layout.spinner_custom_dark_item6)

                val popupWindow = ListPopupWindow(mContext!!)
                popupWindow.setAdapter(adapter)

                popupWindow.anchorView = view_separator_language
                popupWindow.width = text_view_language.measuredWidth
                popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
                popupWindow.isModal = true
                popupWindow.inputMethodMode = ListPopupWindow.INPUT_METHOD_NOT_NEEDED

                popupWindow.setOnItemClickListener { _, rowView, _, _ ->
                    val selectedLanguage = (rowView as TextView).text.toString().trim()
                    presenter.setLanguage(mContext!!, selectedLanguage)

                    if (popupWindow.isShowing) {
                        popupWindow.dismiss()
                    }
                }

                popupWindow.show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_FOR_TOKEN_VERIFICATION) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    SharedPrefUtils.write(
                        Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON,
                        false
                    )
                }

                Activity.RESULT_CANCELED -> {
                    switch_compat_2_factor_authentication.isChecked = true
                }
            }
        }
    }

    override fun onSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message, true)
        SharedPrefUtils.write(
            Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON,
            true
        )
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
        switch_compat_2_factor_authentication.isChecked = false
    }

    override fun onSettingLanguage(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message, true)
    }

    override fun onSettingLanguageError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onGettingSettings(language: String) {
        text_view_language.text =
            String.format(
                Locale.getDefault(),
                getString(R.string.settings_language_details),
                language.toTitleCase()
            )

        switch_compat_2_factor_authentication.isChecked =
            SharedPrefUtils.readBoolean(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET_AND_ON)
    }
}