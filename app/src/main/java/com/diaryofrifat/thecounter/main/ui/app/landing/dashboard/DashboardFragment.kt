package com.diaryofrifat.thecounter.main.ui.app.landing.dashboard

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.local.model.DashboardItem
import com.diaryofrifat.thecounter.main.ui.app.landing.container.ContainerActivity
import com.diaryofrifat.thecounter.main.ui.base.callback.ItemClickListener
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.initializeRecyclerView
import com.diaryofrifat.thecounter.main.ui.base.toTitleCase
import com.diaryofrifat.thecounter.utils.helper.ShareUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment<DashboardMvpView, DashboardPresenter>(), DashboardMvpView {

    override val layoutId: Int
        get() = R.layout.fragment_dashboard

    override fun getFragmentPresenter(): DashboardPresenter {
        return DashboardPresenter()
    }

    override fun startUI() {
        setListeners()
        initialize()
        loadData()
    }

    private fun loadData() {
        if (mContext != null) {
            presenter.getDashboardData(mContext!!)
        }
    }

    private fun setListeners() {

    }

    private fun initialize() {
        (activity as ContainerActivity).setPageTitle(getString(R.string.dashboard_toolbar_title).toTitleCase())
        fab_clock.isEnabled = false

        val layoutManager = GridLayoutManager(mContext!!, 6)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0, 1, 2, 3 -> 3
                    4 -> 6
                    5, 6, 7, 8, 9, 10 -> 2
                    11, 12, 13, 14 -> 3
                    else -> 6
                }
            }
        }

        initializeRecyclerView(
            recycler_view_dashboard_items,
            DashboardAdapter(),
            object : ItemClickListener<DashboardItem> {
                override fun onItemClick(view: View, item: DashboardItem) {
                    super.onItemClick(view, item)

                    when (view.id) {
                        R.id.image_view_copy_url -> {
                            presenter.copyTextToClipboard(mContext!!, item.content1!!)
                        }

                        R.id.image_view_facebook -> {
                            if (activity != null) {
                                ShareUtils.shareUrlToFacebook(
                                    activity!!,
                                    item.content1!!
                                )
                            }
                        }

                        R.id.image_view_twitter -> {
                            if (activity != null) {
                                ShareUtils.shareToTwitter(
                                    activity!!,
                                    null,
                                    item.content1,
                                    null,
                                    null
                                )
                            }
                        }
                    }
                }
            },
            null,
            layoutManager,
            null,
            null,
            null,
            null
        )
    }

    private fun getAdapter(): DashboardAdapter {
        return recycler_view_dashboard_items.adapter as DashboardAdapter
    }

    override fun stopUI() {

    }

    override fun onResult() {

    }

    override fun onSuccess(list: List<DashboardItem>) {
        getAdapter().clear()
        getAdapter().addItems(list)
        ProgressDialogUtils.on().hideProgressDialog()
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}