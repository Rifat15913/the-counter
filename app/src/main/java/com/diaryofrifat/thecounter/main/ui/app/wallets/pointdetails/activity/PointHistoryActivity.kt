package com.diaryofrifat.thecounter.main.ui.app.wallets.pointdetails.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.remote.model.PointActivity
import com.diaryofrifat.thecounter.main.ui.app.others.fullscreenphoto.PhotoFullScreenActivity
import com.diaryofrifat.thecounter.main.ui.base.callback.ItemClickListener
import com.diaryofrifat.thecounter.main.ui.base.callback.PaginationListener
import com.diaryofrifat.thecounter.main.ui.base.component.BaseActivity
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.initializeRecyclerView
import com.diaryofrifat.thecounter.main.ui.base.makeItGone
import com.diaryofrifat.thecounter.main.ui.base.makeItVisible
import com.diaryofrifat.thecounter.main.ui.base.setRipple
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.activity_point_history.*


class PointHistoryActivity : BaseActivity<PointActivityMvpView, PointActivityPresenter>(),
    PointActivityMvpView, PaginationListener {

    companion object {

        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context, walletBalance: String) {
            val intent = Intent(context, PointHistoryActivity::class.java)
            intent.putExtra(Constants.IntentKeys.WALLET_BALANCE, walletBalance)
            runCurrentActivity(context, intent)
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_point_history

    override fun getActivityPresenter(): PointActivityPresenter {
        return PointActivityPresenter()
    }

    override fun startUI() {
        extractDataFromIntent()
        initialize()
        setListeners()
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

        initializeRecyclerView(
            recycler_view_history,
            PointActivityAdapter(this),
            object : ItemClickListener<PointActivity> {
                @SuppressLint("SetJavaScriptEnabled")
                override fun onItemClick(view: View, item: PointActivity) {
                    super.onItemClick(view, item)

                    when (view.id) {
                        R.id.text_view_approve -> {
                            presenter.confirmPointWithdrawalHistory(
                                this@PointHistoryActivity,
                                item
                            )
                        }

                        R.id.image_view_bank_receipt -> {
                            if (!TextUtils.isEmpty(item.bankReceiptImageUrl)) {
                                if (item.bankReceiptImageUrl?.endsWith(".pdf")!!) {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(item.bankReceiptImageUrl)
                                        )
                                    )
                                } else {
                                    PhotoFullScreenActivity.startActivity(
                                        this@PointHistoryActivity,
                                        item.bankReceiptImageUrl
                                    )
                                }
                            }
                        }
                    }
                }
            },
            null,
            LinearLayoutManager(this),
            null,
            null,
            null,
            null
        )
    }

    private fun loadData() {
        presenter.getPointHistory(
            this,
            DataUtils.getInteger(R.integer.page_one)
        )
    }

    private fun getAdapter(): PointActivityAdapter {
        return recycler_view_history.adapter as PointActivityAdapter
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
        presenter.getPointHistory(
            this,
            (currentPage + 1)
        )
    }

    override fun onSuccess(list: List<PointActivity>) {
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

    override fun onConfirmation(message: String, item: PointActivity) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
        getAdapter().addItem(item)
    }
}