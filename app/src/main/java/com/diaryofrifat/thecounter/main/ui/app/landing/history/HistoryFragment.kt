package com.diaryofrifat.thecounter.main.ui.app.landing.history

import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.app.landing.container.ContainerActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.utils.helper.ViewUtils

class HistoryFragment : BaseFragment<HistoryMvpView, HistoryPresenter>(), HistoryMvpView {
    override val layoutId: Int
        get() = R.layout.fragment_history

    override fun getFragmentPresenter(): HistoryPresenter {
        return HistoryPresenter()
    }

    override fun startUI() {
        initialize()
    }

    private fun initialize() {
        (activity as ContainerActivity).isVisibleFirstAction(false)
        (activity as ContainerActivity).isVisibleSecondAction(true)

        ViewUtils.getDrawable(R.drawable.selector_ic_search)?.let {
            (activity as ContainerActivity).setSecondAction(
                it,
                getString(R.string.container_search)
            )
        }
    }

    override fun stopUI() {

    }
}