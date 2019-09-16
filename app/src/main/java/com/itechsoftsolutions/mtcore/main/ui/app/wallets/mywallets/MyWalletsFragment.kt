package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets

import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.app.landing.container.ContainerActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.main.ui.base.toTitleCase
import kotlinx.android.synthetic.main.fragment_my_wallets.*

class MyWalletsFragment : BaseFragment<MyWalletsMvpView, MyWalletsPresenter>() {

    private var mAdapter: MyWalletsTabViewPagerAdapter? = null

    override val layoutId: Int
        get() = R.layout.fragment_my_wallets

    override fun getFragmentPresenter(): MyWalletsPresenter {
        return MyWalletsPresenter()
    }

    override fun startUI() {
        initialize()
    }

    private fun initialize() {
        (activity as ContainerActivity).setPageTitle(getString(R.string.my_wallets_title).toTitleCase())
        setupViewPager()
    }

    override fun stopUI() {

    }

    private fun setupViewPager() {
        tab_layout.setupWithViewPager(view_pager)
        mAdapter = MyWalletsTabViewPagerAdapter(childFragmentManager, mContext!!)
        view_pager.adapter = mAdapter
    }

}