package com.itechsoftsolutions.mtcore.main.ui.app.wallets.biddetails.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.remote.model.BidActivity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.PaginationListener
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseActivity
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.initializeRecyclerView
import com.itechsoftsolutions.mtcore.main.ui.base.makeItGone
import com.itechsoftsolutions.mtcore.main.ui.base.makeItVisible
import com.itechsoftsolutions.mtcore.main.ui.base.setRipple
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.ViewUtils
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.activity_bid_history.*
import kotlinx.android.synthetic.main.item_bid_activity.view.*

class BidHistoryActivity : BaseActivity<BidActivityMvpView, BidActivityPresenter>(),
    BidActivityMvpView, PaginationListener {

    companion object {

        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context, walletBalance: String) {
            val intent = Intent(context, BidHistoryActivity::class.java)
            intent.putExtra(Constants.IntentKeys.WALLET_BALANCE, walletBalance)
            runCurrentActivity(context, intent)
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_bid_history

    override fun getActivityPresenter(): BidActivityPresenter {
        return BidActivityPresenter()
    }

    override fun startUI() {
        extractDataFromIntent()
        setListeners()
        initialize()
        loadData()
    }

    override fun stopUI() {

    }

    private fun extractDataFromIntent() {
        if (intent != null) {
            val bundle = intent.extras
            if (bundle != null) {
                if (bundle.containsKey(Constants.IntentKeys.WALLET_BALANCE)) {
                    presenter.walletBalance = bundle.getString(Constants.IntentKeys.WALLET_BALANCE)
                }
            }
        }
    }

    private fun setListeners() {
        setClickListener(image_view_navigator)
    }

    private fun initialize() {
        presenter.walletBalance?.let {
            text_view_amount.text = it
        }

        image_view_navigator.setRipple(R.color.colorPrimary50)

        val desiredTypeface = ViewUtils.getFont(R.font.bold)
        layout_header.text_view_time.typeface = desiredTypeface
        layout_header.text_view_amount.typeface = desiredTypeface

        layout_header.text_view_time.text = getString(R.string.bid_activity_date_and_time)
        layout_header.text_view_amount.text = getString(R.string.bid_activity_amount)

        initializeRecyclerView(
            recycler_view_history,
            BidActivityAdapter(this),
            null,
            null,
            LinearLayoutManager(this),
            null,
            null,
            null,
            null
        )
    }

    private fun loadData() {
        presenter.getBidsHistory(
            this,
            DataUtils.getInteger(R.integer.page_one)
        )
    }

    private fun getAdapter(): BidActivityAdapter {
        return recycler_view_history.adapter as BidActivityAdapter
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_navigator -> {
                onBackPressed()
            }
        }
    }

    override fun onNextPage(currentPage: Int) {
        presenter.getBidsHistory(
            this,
            (currentPage + 1)
        )
    }

    override fun onSuccess(list: List<BidActivity>) {
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

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}