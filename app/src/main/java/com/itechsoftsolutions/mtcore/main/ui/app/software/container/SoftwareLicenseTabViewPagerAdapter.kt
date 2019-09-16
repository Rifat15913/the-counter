package com.itechsoftsolutions.mtcore.main.ui.app.software.container

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.app.software.history.SoftwareLicenceHistoryFragment
import com.itechsoftsolutions.mtcore.main.ui.app.software.license.SoftwareLicenseFragment


class SoftwareLicenseTabViewPagerAdapter internal constructor(
    fragmentManager: FragmentManager,
    context: Context
) : FragmentPagerAdapter(fragmentManager) {

    private var mTitleList: MutableList<String>? = null

    init {
        mTitleList = ArrayList()
        mTitleList!!.add(context.getString(R.string.software_license_software_license))
        mTitleList!!.add(context.getString(R.string.software_license_purchase_history))
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                SoftwareLicenseFragment()
            }

            else -> {
                SoftwareLicenceHistoryFragment()
            }
        }
    }

    override fun getCount(): Int {
        return mTitleList!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList!![position]
    }
}


