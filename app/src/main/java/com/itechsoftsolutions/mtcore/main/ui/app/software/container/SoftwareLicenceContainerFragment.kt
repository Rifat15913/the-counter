package com.itechsoftsolutions.mtcore.main.ui.app.software.container

import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.app.landing.container.ContainerActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.main.ui.base.toTitleCase
import kotlinx.android.synthetic.main.fragment_software_license_container.*

class SoftwareLicenceContainerFragment :
    BaseFragment<SoftwareLicenceContainerMvpView, SoftwareLicenceContainerPresenter>() {

    private var mAdapter: SoftwareLicenseTabViewPagerAdapter? = null

    override val layoutId: Int
        get() = R.layout.fragment_software_license_container

    override fun getFragmentPresenter(): SoftwareLicenceContainerPresenter {
        return SoftwareLicenceContainerPresenter()
    }

    override fun startUI() {
        initialize()
    }

    private fun initialize() {
        (activity as ContainerActivity).setPageTitle(getString(R.string.software_license_title).toTitleCase())
        setupViewPager()
    }

    override fun stopUI() {

    }

    private fun setupViewPager() {
        tab_layout.setupWithViewPager(view_pager)
        mAdapter = SoftwareLicenseTabViewPagerAdapter(childFragmentManager, mContext!!)
        view_pager.adapter = mAdapter
    }
}