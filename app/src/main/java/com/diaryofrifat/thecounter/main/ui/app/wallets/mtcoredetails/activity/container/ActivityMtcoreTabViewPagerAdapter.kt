package com.diaryofrifat.thecounter.main.ui.app.wallets.mtcoredetails.activity.container

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.ui.app.wallets.mtcoredetails.activity.tab.ActivityMtcoreTabFragment


class ActivityMtcoreTabViewPagerAdapter internal constructor(
    fragmentManager: FragmentManager,
    context: Context,
    walletID: String
) : FragmentPagerAdapter(fragmentManager) {

    private var mTitleList: MutableList<String>? = null
    private var mWalletID = walletID

    init {
        mTitleList = ArrayList()
        mTitleList!!.add(context.getString(R.string.mtcore_wallet_details_deposit))
        mTitleList!!.add(context.getString(R.string.mtcore_wallet_details_withdrawal))
    }

    override fun getItem(position: Int): Fragment {
        return ActivityMtcoreTabFragment.newInstance(position, mWalletID)
    }

    override fun getCount(): Int {
        return mTitleList!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList!![position]
    }
}


