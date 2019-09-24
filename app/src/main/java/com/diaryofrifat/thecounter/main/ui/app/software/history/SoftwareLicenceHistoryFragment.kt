package com.diaryofrifat.thecounter.main.ui.app.software.history

import androidx.recyclerview.widget.LinearLayoutManager
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareEntity
import com.diaryofrifat.thecounter.main.data.remote.model.SoftwareLicensePurchaseHistory
import com.diaryofrifat.thecounter.main.ui.base.callback.PaginationListener
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.initializeRecyclerView
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.helper.ViewUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_software_license_purchase_history.*
import kotlinx.android.synthetic.main.item_software_license_purchase_history.view.*

class SoftwareLicenceHistoryFragment :
    BaseFragment<SoftwareLicenceHistoryMvpView, SoftwareLicenceHistoryPresenter>(),
    SoftwareLicenceHistoryMvpView, PaginationListener {

    override val layoutId: Int
        get() = R.layout.fragment_software_license_purchase_history

    override fun getFragmentPresenter(): SoftwareLicenceHistoryPresenter {
        return SoftwareLicenceHistoryPresenter()
    }

    override fun startUI() {
        initialize()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun initialize() {
        val desiredTypeface = ViewUtils.getFont(R.font.bold)
        layout_header.text_view_software_name.typeface = desiredTypeface
        layout_header.text_view_price.typeface = desiredTypeface
        layout_header.text_view_status.typeface = desiredTypeface
        layout_header.text_view_date.typeface = desiredTypeface

        layout_header.text_view_software_name.text = getString(R.string.software_license_software)
        layout_header.text_view_price.text = getString(R.string.software_license_price)
        layout_header.text_view_status.text = getString(R.string.software_license_status)
        layout_header.text_view_date.text = getString(R.string.software_license_date)

        initializeRecyclerView(
            recycler_view_history,
            SoftwareLicensePurchaseHistoryAdapter(this),
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

    private fun getAdapter(): SoftwareLicensePurchaseHistoryAdapter {
        return recycler_view_history.adapter as SoftwareLicensePurchaseHistoryAdapter
    }

    private fun loadData() {
        presenter.getSoftwareListFromCloud(mContext!!)
        presenter.getSoftwareListFromDatabase()
        presenter.getSoftwareLicensePurchaseHistory(
            mContext!!,
            DataUtils.getInteger(R.integer.page_one)
        )
    }

    override fun onGettingSoftwares(list: List<SoftwareEntity>) {
        getAdapter().softwareList = list
        getAdapter().notifyDataSetChanged()
    }

    override fun onNextPage(currentPage: Int) {
        presenter.getSoftwareLicensePurchaseHistory(
            mContext!!,
            (currentPage + 1)
        )
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onGettingSoftwareLicensePurchaseHistory(list: List<SoftwareLicensePurchaseHistory>) {
        if (list.isEmpty() || list[list.size - 1].currentPage == DataUtils.getInteger(R.integer.page_one)) {
            getAdapter().clear()
        }
        getAdapter().addItems(list)
        ProgressDialogUtils.on().hideProgressDialog()
    }
}