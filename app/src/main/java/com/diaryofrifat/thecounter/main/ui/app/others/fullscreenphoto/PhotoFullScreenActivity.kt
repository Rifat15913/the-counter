package com.diaryofrifat.thecounter.main.ui.app.others.fullscreenphoto

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.base.component.BaseActivity
import com.diaryofrifat.thecounter.main.ui.base.setRipple
import com.diaryofrifat.thecounter.utils.libs.GlideUtils
import kotlinx.android.synthetic.main.activity_photo_full_screen.*

class PhotoFullScreenActivity : BaseActivity<PhotoFullScreenMvpView, PhotoFullScreenPresenter>(),
    PhotoFullScreenMvpView {

    companion object {

        /**
         * This method starts current activity
         *
         * @param context UI context
         * @param imageUrl url of the image
         * */
        fun startActivity(context: Context, imageUrl: String) {
            val intent = Intent(context, PhotoFullScreenActivity::class.java)
            intent.putExtra(PhotoFullScreenActivity::class.java.simpleName, imageUrl)
            runCurrentActivity(context, intent)
        }
    }

    private var mImageUrl: String? = null

    override val layoutResourceId: Int
        get() = R.layout.activity_photo_full_screen

    override fun getActivityPresenter(): PhotoFullScreenPresenter {
        return PhotoFullScreenPresenter()
    }

    override fun startUI() {
        extractDataFromIntent()
        setListeners()
        initialize()
        loadData()
    }

    override fun stopUI() {

    }

    private fun extractDataFromIntent() {
        if (intent != null) {
            val bundle = intent.extras
            if (bundle != null) {
                if (bundle.containsKey(PhotoFullScreenActivity::class.java.simpleName)) {
                    mImageUrl =
                        bundle.getString(PhotoFullScreenActivity::class.java.simpleName)
                }
            }
        }
    }

    private fun setListeners() {
        setClickListener(image_view_navigator)
    }

    private fun initialize() {
        image_view_navigator.setRipple(R.color.colorPrimary50)
    }

    private fun loadData() {
        if (!TextUtils.isEmpty(mImageUrl)) {
            GlideUtils.normalWithCaching(image_view_bank_receipt, mImageUrl!!)
        }
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_navigator -> {
                onBackPressed()
            }
        }
    }
}