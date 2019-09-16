package com.itechsoftsolutions.mtcore.main.ui.app.profile.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.remote.model.ProfileActivity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.PaginationListener
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.initializeRecyclerView
import com.itechsoftsolutions.mtcore.main.ui.base.makeItGone
import com.itechsoftsolutions.mtcore.main.ui.base.makeItVisible
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.activity_point_history.*
import kotlinx.android.synthetic.main.fragment_software_license_purchase_history.recycler_view_history

class ProfileHistoryFragment :
    BaseFragment<ProfileActivityMvpView, ProfileActivityPresenter>(),
    ProfileActivityMvpView, PaginationListener {

    override val layoutId: Int
        get() = R.layout.fragment_profile_history

    override fun getFragmentPresenter(): ProfileActivityPresenter {
        return ProfileActivityPresenter()
    }

    override fun startUI() {
        initialize()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun initialize() {
        initializeRecyclerView(
            recycler_view_history,
            ProfileActivityAdapter(this),
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

    private fun getAdapter(): ProfileActivityAdapter {
        return recycler_view_history.adapter as ProfileActivityAdapter
    }

    private fun loadData() {
        presenter.getProfileActivity(
            mContext!!,
            DataUtils.getInteger(R.integer.page_one)
        )
    }

    override fun onNextPage(currentPage: Int) {
        presenter.getProfileActivity(
            mContext!!,
            (currentPage + 1)
        )
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onGettingHistory(list: List<ProfileActivity>) {
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