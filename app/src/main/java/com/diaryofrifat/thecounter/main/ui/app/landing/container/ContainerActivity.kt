package com.diaryofrifat.thecounter.main.ui.app.landing.container

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.base.component.BaseActivity
import com.diaryofrifat.thecounter.utils.helper.ViewUtils
import kotlinx.android.synthetic.main.activity_container.*


class ContainerActivity : BaseActivity<ContainerMvpView, ContainerPresenter>() {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, ContainerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            runCurrentActivity(context, intent)
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_container

    override fun getActivityPresenter(): ContainerPresenter {
        return ContainerPresenter()
    }

    override fun startUI() {
        setListeners()
        initialize()
        loadData()
    }

    override fun stopUI() {

    }

    override fun onBackPressed() {
        /*if (currentFragment is DashboardFragment) {
            finish()
            super.onBackPressed()
        } else {
            // visitDashboard()
        }*/
    }

    override fun onClick(view: View) {
        when (view.id) {

        }
    }

    private fun loadData() {

    }

    private fun setListeners() {
        setClickListener(image_view_first_action, image_view_second_action)
    }

    private fun initialize() {
        // Handle status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtils.setLightStatusBar(this)
            ViewUtils.setStatusBarColor(this, R.color.colorPrimary)
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewUtils.setStatusBarColor(this, R.color.colorAccent)
        } else {
            // Do nothing for Jelly bean and Kitkat devices
        }

        // Handle navigation bar color
        ViewUtils.setLightSystemNavigationBar(this)
        ViewUtils.setSystemNavigationBarColor(this, R.color.colorPrimary)
    }

    fun setPageTitle(title: String) {
        text_view_title.text = title
    }

    fun visitDashboard() {
        //commitFragment(R.id.constraint_layout_full_fragment_container, DashboardFragment())
    }
}