package com.diaryofrifat.thecounter.main.ui.app.landing.counter

import android.view.View
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.app.landing.container.ContainerActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.SharedPrefUtils
import com.diaryofrifat.thecounter.utils.helper.ViewUtils
import kotlinx.android.synthetic.main.fragment_counter.*

class CounterFragment : BaseFragment<CounterMvpView, CounterPresenter>(), CounterMvpView {

    private var count: Long = 0

    override val layoutId: Int
        get() = R.layout.fragment_counter

    override fun getFragmentPresenter(): CounterPresenter {
        return CounterPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
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

    private fun setListeners() {
        setClickListener(button_minus, button_plus)
    }

    override fun onResume() {
        super.onResume()

        count = SharedPrefUtils.readLong(Constants.PreferenceKeys.CURRENT_COUNT)
        setCount()
    }

    private fun setCount() {
        text_view_count.text = count.toString()

        text_view_times.text = getString(
            if (count < 2) {
                R.string.counter_time
            } else {
                R.string.counter_times
            }
        )
    }

    override fun stopUI() {

    }

    override fun onStop() {
        super.onStop()
        SharedPrefUtils.write(Constants.PreferenceKeys.CURRENT_COUNT, count)
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.button_minus -> {
                if (count > 0) {
                    count -= 1
                    setCount()
                }
            }

            R.id.button_plus -> {
                count += 1
                setCount()
            }
        }
    }

    fun resetCount() {
        count = 0
        setCount()
    }
}