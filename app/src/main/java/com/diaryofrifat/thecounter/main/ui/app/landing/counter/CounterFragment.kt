package com.diaryofrifat.thecounter.main.ui.app.landing.counter

import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.app.landing.container.ContainerActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.utils.helper.ViewUtils

class CounterFragment : BaseFragment<CounterMvpView, CounterPresenter>(), CounterMvpView {
    override val layoutId: Int
        get() = R.layout.fragment_counter

    override fun getFragmentPresenter(): CounterPresenter {
        return CounterPresenter()
    }

    override fun startUI() {
        initialize()
    }

    private fun initialize() {
        (activity as ContainerActivity).setPageTitle(getString(R.string.container_counter))

        (activity as ContainerActivity).isVisibleFirstAction(true)
        (activity as ContainerActivity).isVisibleSecondAction(true)

        ViewUtils.getDrawable(R.drawable.selector_ic_search)?.let {
            (activity as ContainerActivity).setFirstAction(
                it,
                getString(R.string.container_search)
            )
        }

        ViewUtils.getDrawable(R.drawable.selector_ic_reset)?.let {
            (activity as ContainerActivity).setSecondAction(
                it,
                getString(R.string.container_reset)
            )
        }
    }

    override fun stopUI() {

    }
}