package com.diaryofrifat.thecounter.main.ui.app.landing.counter

import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.app.landing.container.ContainerActivity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.setRipple
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.SharedPrefUtils
import com.diaryofrifat.thecounter.utils.helper.ViewUtils
import kotlinx.android.synthetic.main.fragment_counter.*


class CounterFragment : BaseFragment<CounterMvpView, CounterPresenter>(), CounterMvpView {

    companion object {
        const val DELAY_BEFORE_REPEAT: Long = 100
    }

    private val repeatUpdateHandler = Handler()
    private var mValue: Long = 0
    private var mIsAutoIncrementOn = false
    private var mIsAutoDecrementOn = false

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
        // Handle the actions
        (activity as ContainerActivity).isVisibleFirstAction(true)
        (activity as ContainerActivity).isVisibleSecondAction(true)

        ViewUtils.getDrawable(R.drawable.selector_ic_save)?.let {
            (activity as ContainerActivity).setFirstAction(
                it,
                getString(R.string.container_save)
            )
        }

        ViewUtils.getDrawable(R.drawable.selector_ic_reset)?.let {
            (activity as ContainerActivity).setSecondAction(
                it,
                getString(R.string.container_reset)
            )
        }

        // Handle drawables for buttons
        val screenWidth = ViewUtils.getScreenWidth(mContext!!)
        GradientDrawable().apply {
            this.setColor(ViewUtils.getColor(R.color.colorAccent))
            this.setSize(screenWidth, screenWidth / 2)
            this.cornerRadii = floatArrayOf(
                screenWidth.toFloat(),
                screenWidth.toFloat(),
                screenWidth.toFloat(),
                screenWidth.toFloat(),
                0f,
                0f,
                0f,
                0f
            )

            text_view_plus.background = this
        }

        GradientDrawable().apply {
            this.setColor(ViewUtils.getColor(R.color.colorNegativeRed))
            this.shape = GradientDrawable.OVAL
            text_view_minus.background = this
        }

        // Handle the ripples
        text_view_plus.setRipple(R.color.colorWhite50)
        text_view_minus.setRipple(R.color.colorWhite50)
    }

    private fun setListeners() {
        setClickListener(text_view_plus, text_view_minus)

        text_view_plus.setOnLongClickListener {
            mIsAutoIncrementOn = true
            repeatUpdateHandler.post(CountUpdater())
            false
        }

        text_view_minus.setOnLongClickListener {
            mIsAutoDecrementOn = true
            repeatUpdateHandler.post(CountUpdater())
            false
        }

        text_view_plus.setOnTouchListener { _, event ->
            if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)
                && mIsAutoIncrementOn
            ) {
                mIsAutoIncrementOn = false
            }

            false
        }

        text_view_minus.setOnTouchListener { _, event ->
            if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)
                && mIsAutoDecrementOn
            ) {
                mIsAutoDecrementOn = false
            }

            false
        }
    }

    override fun onResume() {
        super.onResume()

        mValue = SharedPrefUtils.readLong(Constants.PreferenceKeys.CURRENT_COUNT)
        setCount()
    }

    private fun setCount() {
        text_view_count.text = mValue.toString()

        text_view_times.text = getString(
            if (mValue < 2) {
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
        SharedPrefUtils.write(Constants.PreferenceKeys.CURRENT_COUNT, mValue)
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.text_view_minus -> {
                decrementCounter()
            }

            R.id.text_view_plus -> {
                incrementCounter()
            }
        }
    }

    private fun decrementCounter() {
        if (mValue > 0) {
            mValue -= 1
            setCount()
        }
    }

    private fun incrementCounter() {
        mValue += 1
        setCount()
    }

    fun resetCount() {
        mValue = 0
        setCount()
    }

    inner class CountUpdater : Runnable {
        override fun run() {
            if (mIsAutoIncrementOn) {
                incrementCounter()
                repeatUpdateHandler.postDelayed(CountUpdater(), DELAY_BEFORE_REPEAT)
            } else if (mIsAutoDecrementOn) {
                decrementCounter()
                repeatUpdateHandler.postDelayed(CountUpdater(), DELAY_BEFORE_REPEAT)
            }
        }
    }
}