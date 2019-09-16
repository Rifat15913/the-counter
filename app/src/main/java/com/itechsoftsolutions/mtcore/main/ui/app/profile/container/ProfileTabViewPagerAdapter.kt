package com.itechsoftsolutions.mtcore.main.ui.app.profile.container

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.app.profile.activity.ProfileHistoryFragment
import com.itechsoftsolutions.mtcore.main.ui.app.profile.address.AddressFragment
import com.itechsoftsolutions.mtcore.main.ui.app.profile.bank.BankDetailsFragment
import com.itechsoftsolutions.mtcore.main.ui.app.profile.basicinfo.BasicInformationFragment
import com.itechsoftsolutions.mtcore.main.ui.app.profile.id.IDVerificationFragment
import com.itechsoftsolutions.mtcore.main.ui.app.profile.personalinfo.PersonalInformationFragment


class ProfileTabViewPagerAdapter internal constructor(
    fragmentManager: FragmentManager,
    context: Context
) : FragmentPagerAdapter(fragmentManager) {

    private var mTitleList: MutableList<String>? = null

    init {
        mTitleList = ArrayList()
        mTitleList!!.add(context.getString(R.string.profile_basic_info))
        mTitleList!!.add(context.getString(R.string.profile_personal_info))
        mTitleList!!.add(context.getString(R.string.profile_address))
        mTitleList!!.add(context.getString(R.string.profile_id))
        mTitleList!!.add(context.getString(R.string.profile_bank_account))
        mTitleList!!.add(context.getString(R.string.profile_activity_log))
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                BasicInformationFragment()
            }

            1 -> {
                PersonalInformationFragment()
            }

            2 -> {
                AddressFragment()
            }

            3 -> {
                IDVerificationFragment()
            }

            4 -> {
                BankDetailsFragment()
            }

            else -> {
                ProfileHistoryFragment()
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


