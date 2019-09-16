package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mtcoredetails.activity.container

import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import kotlinx.android.synthetic.main.fragment_activity_mtcore_container.*

class ActivityMtcoreContainerFragment :
    BaseFragment<ActivityMtcoreContainerMvpView, ActivityMtcoreContainerPresenter>(),
    ActivityMtcoreContainerMvpView {

    private var mAdapter: ActivityMtcoreTabViewPagerAdapter? = null

    override val layoutId: Int
        get() = R.layout.fragment_activity_mtcore_container

    override fun getFragmentPresenter(): ActivityMtcoreContainerPresenter {
        return ActivityMtcoreContainerPresenter()
    }

    override fun startUI() {
        extractDataFromArguments()
        initialize()
        setListeners()
        loadData()
    }

    override fun stopUI() {

    }

    private fun extractDataFromArguments() {
        val bundle = arguments

        if (bundle != null) {
            if (bundle.containsKey(Constants.IntentKeys.WALLET_ID)) {
                presenter.walletID = bundle.getString(Constants.IntentKeys.WALLET_ID)
            }
        }
    }

    private fun initialize() {
        if (!TextUtils.isEmpty(presenter.walletID)) {
            setupViewPager()
        }
    }

    private fun setupViewPager() {
        tab_layout.setupWithViewPager(view_pager)
        mAdapter =
            ActivityMtcoreTabViewPagerAdapter(
                childFragmentManager,
                mContext!!,
                presenter.walletID!!
            )
        view_pager.adapter = mAdapter
    }

    private fun setListeners() {
        setClickListener()
    }

    private fun loadData() {
        presenter.walletID?.let {

        }
    }
}