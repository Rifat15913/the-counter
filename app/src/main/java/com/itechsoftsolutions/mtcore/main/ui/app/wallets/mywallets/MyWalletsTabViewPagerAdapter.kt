package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets.bid.BidWalletFragment
import com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets.mtcore.MTCoreWalletFragment
import com.itechsoftsolutions.mtcore.main.ui.app.wallets.mywallets.point.PointWalletFragment


class MyWalletsTabViewPagerAdapter internal constructor(
    fragmentManager: FragmentManager,
    context: Context
) :
    FragmentPagerAdapter(fragmentManager) {

    private var mTitleList: MutableList<String>? = null

    init {
        mTitleList = ArrayList()
        mTitleList!!.add(context.getString(R.string.my_wallets_mtcore))
        mTitleList!!.add(context.getString(R.string.my_wallets_bids))
        mTitleList!!.add(context.getString(R.string.my_wallets_points))
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                MTCoreWalletFragment()
            }

            1 -> {
                BidWalletFragment()
            }

            else -> {
                PointWalletFragment()
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


