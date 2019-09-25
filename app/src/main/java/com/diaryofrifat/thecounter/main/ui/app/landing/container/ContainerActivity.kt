package com.diaryofrifat.thecounter.main.ui.app.landing.container

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.app.landing.counter.CounterFragment
import com.diaryofrifat.thecounter.main.ui.app.landing.history.HistoryFragment
import com.diaryofrifat.thecounter.main.ui.app.landing.settings.SettingsFragment
import com.diaryofrifat.thecounter.main.ui.base.component.BaseActivity
import com.diaryofrifat.thecounter.main.ui.base.helper.AlertDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.makeItGone
import com.diaryofrifat.thecounter.main.ui.base.makeItVisible
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
        if (currentFragment is CounterFragment) {
            finish()
            super.onBackPressed()
        } else {
            visitCounter()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.image_view_second_action -> {
                when (view.tag) {
                    getString(R.string.container_reset) -> {
                        if (currentFragment is CounterFragment) {
                            AlertDialogUtils.on().showNativeDialog(
                                this@ContainerActivity,
                                true,
                                getString(R.string.yes),
                                DialogInterface.OnClickListener { dialog, _ ->
                                    dialog.dismiss()
                                    (currentFragment as CounterFragment).resetCount()
                                },
                                getString(R.string.no),
                                DialogInterface.OnClickListener { dialog, _ ->
                                    dialog.dismiss()
                                },
                                getString(R.string.are_you_sure),
                                null,
                                null
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadData() {

    }

    private fun setListeners() {
        setClickListener(image_view_first_action, image_view_second_action)

        bottom_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_counter -> {
                    visitCounter()
                }

                R.id.action_history -> {
                    visitHistory()
                }

                R.id.action_settings -> {
                    visitSettings()
                }
            }

            true
        }
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

        // Handle initial page setup
        visitCounter()
    }

    fun setPageTitle(title: String) {
        text_view_title.text = title
    }

    fun setFirstAction(drawable: Drawable, tag: String) {
        image_view_first_action.setImageDrawable(drawable)
        image_view_first_action.tag = tag
    }

    fun setSecondAction(drawable: Drawable, tag: String) {
        image_view_second_action.setImageDrawable(drawable)
        image_view_second_action.tag = tag
    }

    fun isVisibleFirstAction(isVisible: Boolean) {
        if (isVisible) {
            image_view_first_action.makeItVisible()
        } else {
            image_view_first_action.makeItGone()
        }
    }

    fun isVisibleSecondAction(isVisible: Boolean) {
        if (isVisible) {
            image_view_second_action.makeItVisible()
        } else {
            image_view_second_action.makeItGone()
        }
    }

    fun visitCounter() {
        commitFragment(R.id.constraint_layout_fragment_container, CounterFragment())
    }

    fun visitHistory() {
        commitFragment(R.id.constraint_layout_fragment_container, HistoryFragment())
    }

    fun visitSettings() {
        commitFragment(R.id.constraint_layout_fragment_container, SettingsFragment())
    }
}