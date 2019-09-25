package com.diaryofrifat.thecounter.main.ui.app.landing.settings

import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.app.landing.container.ContainerActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.utils.helper.ViewUtils

class SettingsFragment : BaseFragment<SettingsMvpView, SettingsPresenter>(), SettingsMvpView {
    override val layoutId: Int
        get() = R.layout.fragment_settings

    override fun getFragmentPresenter(): SettingsPresenter {
        return SettingsPresenter()
    }

    override fun startUI() {
        initialize()
    }

    private fun initialize() {
        (activity as ContainerActivity).setPageTitle(getString(R.string.container_settings))

        (activity as ContainerActivity).isVisibleFirstAction(false)
        (activity as ContainerActivity).isVisibleSecondAction(true)

        ViewUtils.getDrawable(R.drawable.selector_ic_share)?.let {
            (activity as ContainerActivity).setSecondAction(
                it,
                getString(R.string.settings_share)
            )
        }
    }

    override fun stopUI() {

    }
}