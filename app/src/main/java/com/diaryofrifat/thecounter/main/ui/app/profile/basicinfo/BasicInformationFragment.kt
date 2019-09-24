package com.diaryofrifat.thecounter.main.ui.app.profile.basicinfo

import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserEntity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_basic_information.*

class BasicInformationFragment :
    BaseFragment<BasicInformationMvpView, BasicInformationPresenter>(),
    BasicInformationMvpView {

    override val layoutId: Int
        get() = R.layout.fragment_basic_information

    override fun getFragmentPresenter(): BasicInformationPresenter {
        return BasicInformationPresenter()
    }

    override fun startUI() {
        setListeners()
        loadData()
    }

    private fun setListeners() {
        setClickListener(text_view_save_changes)
    }

    private fun loadData() {
        presenter.getUserFromDatabase()
        presenter.getUserFromCloud(mContext!!)
    }

    override fun stopUI() {

    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.text_view_save_changes -> {
                if (!TextUtils.isEmpty(edit_text_first_name.text)
                    && !TextUtils.isEmpty(edit_text_last_name.text)
                    && !TextUtils.isEmpty(edit_text_email.text)
                ) {
                    if (Patterns.EMAIL_ADDRESS.matcher(edit_text_email.text.toString().trim()).matches()) {
                        presenter.updateNameAndEmail(
                            mContext!!,
                            edit_text_first_name.text.toString().trim(),
                            edit_text_last_name.text.toString().trim(),
                            edit_text_email.text.toString().trim()
                        )
                    } else {
                        onError(getString(R.string.forgot_password_error_valid_email))
                    }
                } else {
                    onError(getString(R.string.login_error_valid_fields))
                }
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
        presenter.getUserFromCloud(mContext!!)
    }

    override fun onGettingUser(user: UserEntity) {
        edit_text_first_name.setText(user.firstName)
        edit_text_last_name.setText(user.lastName)
        edit_text_email.setText(user.email)
    }
}