package com.diaryofrifat.thecounter.main.ui.app.myreferral

import android.text.TextUtils
import android.view.View
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.app.landing.container.ContainerActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.setRipple
import com.diaryofrifat.thecounter.main.ui.base.toTitleCase
import com.diaryofrifat.thecounter.utils.helper.ShareUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_my_referral.*
import kotlinx.android.synthetic.main.item_share_my_referral.view.*

class MyReferralFragment : BaseFragment<MyReferralMvpView, MyReferralPresenter>(), MyReferralMvpView {

    override val layoutId: Int
        get() = R.layout.fragment_my_referral

    override fun getFragmentPresenter(): MyReferralPresenter {
        return MyReferralPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
        presenter.getReferralLink(mContext!!)
    }

    private fun initialize() {
        (activity as ContainerActivity).setPageTitle(getString(R.string.my_referral_title).toTitleCase())

        layout_share_my_referral.image_view_copy_url.setRipple(R.color.colorWhite50)
        layout_share_my_referral.image_view_facebook.setRipple(R.color.colorWhite50)
        layout_share_my_referral.image_view_twitter.setRipple(R.color.colorWhite50)
        text_view_copy_url.setRipple(R.color.colorWhite50)
    }

    private fun setListeners() {
        setClickListener(
            layout_share_my_referral.image_view_copy_url,
            layout_share_my_referral.image_view_facebook,
            layout_share_my_referral.image_view_twitter,
            edit_text_url,
            text_view_copy_url
        )
    }

    override fun stopUI() {

    }

    override fun onSuccess(url: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        edit_text_url.setText(url)
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.edit_text_url, R.id.text_view_copy_url, R.id.image_view_copy_url -> {
                if (edit_text_url.text != null && !TextUtils.isEmpty(edit_text_url.text.toString())) {
                    presenter.copyTextToClipboard(mContext!!, edit_text_url.text.toString().trim())
                } else {
                    onError(getString(R.string.my_referral_error_could_not_get_url))
                }
            }

            R.id.image_view_facebook -> {
                if (activity != null
                    && edit_text_url.text != null
                    && !TextUtils.isEmpty(edit_text_url.text.toString().trim())
                ) {
                    ShareUtils.shareUrlToFacebook(activity!!, edit_text_url.text.toString().trim())
                }
            }

            R.id.image_view_twitter -> {
                if (activity != null
                    && edit_text_url.text != null
                    && !TextUtils.isEmpty(edit_text_url.text.toString().trim())
                ) {
                    ShareUtils.shareToTwitter(activity!!, null, edit_text_url.text.toString().trim(), null, null)
                }
            }
        }
    }
}