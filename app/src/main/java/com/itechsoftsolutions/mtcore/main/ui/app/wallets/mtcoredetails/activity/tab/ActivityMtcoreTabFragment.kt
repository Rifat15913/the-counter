package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mtcoredetails.activity.tab

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.remote.model.MtcoreWalletActivity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.PaginationListener
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.initializeRecyclerView
import com.itechsoftsolutions.mtcore.main.ui.base.makeItGone
import com.itechsoftsolutions.mtcore.main.ui.base.makeItVisible
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.ViewUtils
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_activity_tab.*
import kotlinx.android.synthetic.main.item_mtcore_wallet_activity.view.*

class ActivityMtcoreTabFragment :
    BaseFragment<ActivityMtcoreTabMvpView, ActivityMtcoreTabPresenter>(),
    ActivityMtcoreTabMvpView, PaginationListener {

    companion object {
        /**
         * This method starts current activity
         *
         * @param position position of the tab
         * @param walletID id of the wallet
         * */
        fun newInstance(position: Int, walletID: String): BaseFragment<*, *> {
            val fragment = ActivityMtcoreTabFragment()
            val bundle = Bundle()
            bundle.putInt(ActivityMtcoreTabFragment::class.java.simpleName, position)
            bundle.putString(Constants.IntentKeys.WALLET_ID, walletID)
            fragment.arguments = bundle

            return fragment
        }
    }

    private var mTabPosition: Int? = null
    private var mWalletID: String? = null

    override val layoutId: Int
        get() = R.layout.fragment_activity_tab

    override fun getFragmentPresenter(): ActivityMtcoreTabPresenter {
        return ActivityMtcoreTabPresenter()
    }

    override fun startUI() {
        extractDataFromArguments()
        initialize()
    }

    private fun extractDataFromArguments() {
        val bundle = arguments

        if (bundle != null) {
            if (bundle.containsKey(ActivityMtcoreTabFragment::class.java.simpleName)) {
                mTabPosition = bundle.getInt(ActivityMtcoreTabFragment::class.java.simpleName)
            }

            if (bundle.containsKey(Constants.IntentKeys.WALLET_ID)) {
                mWalletID = bundle.getString(Constants.IntentKeys.WALLET_ID)
            }
        }
    }

    private fun initialize() {
        val desiredTypeface = ViewUtils.getFont(R.font.bold)
        layout_header.text_view_first_field.typeface = desiredTypeface
        layout_header.text_view_second_field.typeface = desiredTypeface
        layout_header.text_view_third_field.typeface = desiredTypeface

        layout_header.text_view_first_field.text = getString(R.string.mtcore_wallet_details_time)
        layout_header.text_view_second_field.text =
            getString(R.string.mtcore_wallet_details_address)
        layout_header.text_view_third_field.text = getString(R.string.mtcore_wallet_details_amount)

        initializeRecyclerView(
            recycler_view_history,
            ActivityMtcoreAdapter(this),
            null,
            null,
            LinearLayoutManager(mContext!!),
            null,
            null,
            null,
            null
        )
    }

    override fun stopUI() {

    }

    private fun getAdapter(): ActivityMtcoreAdapter {
        return recycler_view_history.adapter as ActivityMtcoreAdapter
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }


    private fun loadData() {
        if (mTabPosition == 0) {
            presenter.getMtcoreDepositHistory(
                mContext!!,
                mWalletID!!,
                DataUtils.getInteger(R.integer.page_one)
            )
        } else {
            presenter.getMtcoreWithdrawalHistory(
                mContext!!,
                mWalletID!!,
                DataUtils.getInteger(R.integer.page_one)
            )
        }
    }


    override fun onNextPage(currentPage: Int) {
        if (mTabPosition == 0) {
            presenter.getMtcoreDepositHistory(
                mContext!!,
                mWalletID!!,
                (currentPage + 1)
            )
        } else {
            presenter.getMtcoreWithdrawalHistory(
                mContext!!,
                mWalletID!!,
                (currentPage + 1)
            )
        }
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onGettingHistory(list: List<MtcoreWalletActivity>) {
        if (list.isEmpty() || list[list.size - 1].currentPage == DataUtils.getInteger(R.integer.page_one)) {
            getAdapter().clear()
        }

        if (list.isEmpty()) {
            text_view_empty_placeholder.makeItVisible()
        } else {
            text_view_empty_placeholder.makeItGone()
        }

        getAdapter().addItems(list)
        ProgressDialogUtils.on().hideProgressDialog()
    }
}