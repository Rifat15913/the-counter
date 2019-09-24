package com.diaryofrifat.thecounter.main.ui.app.ranking.myranking

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareEntity
import com.diaryofrifat.thecounter.main.data.remote.model.MyRanking
import com.diaryofrifat.thecounter.main.ui.app.landing.container.ContainerActivity
import com.diaryofrifat.thecounter.main.ui.base.*
import com.diaryofrifat.thecounter.main.ui.base.callback.PaginationListener
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import com.diaryofrifat.thecounter.utils.helper.ViewUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_my_ranking.*
import kotlinx.android.synthetic.main.item_dashboard_portrait.view.*


class MyRankingFragment : BaseFragment<MyRankingMvpView, MyRankingPresenter>(),
    MyRankingMvpView, PaginationListener {

    override val layoutId: Int
        get() = R.layout.fragment_my_ranking

    override fun getFragmentPresenter(): MyRankingPresenter {
        return MyRankingPresenter()
    }

    override fun startUI() {
        initialize()
        loadData()
        setListeners()
    }

    private fun initialize() {
        (activity as ContainerActivity).setPageTitle(getString(R.string.my_rankings_title).toTitleCase())

        layout_seller.text_view_title.text = getString(R.string.my_rankings_seller)
        layout_biderator.text_view_title.text = getString(R.string.my_rankings_biderator)
        layout_consumer.text_view_title.text = getString(R.string.my_rankings_consumer)

        layout_seller.constraint_layout_main_container
            .setBackgroundColor(ViewUtils.getColor(R.color.colorDashboardItemDarkBackground))
        layout_biderator.constraint_layout_main_container
            .setBackgroundColor(ViewUtils.getColor(R.color.colorDashboardItemDarkBackground))
        layout_consumer.constraint_layout_main_container
            .setBackgroundColor(ViewUtils.getColor(R.color.colorDashboardItemDarkBackground))

        layout_seller.text_view_title.setTextColor(ViewUtils.getColor(R.color.colorTextRegular))
        layout_seller.text_view_subtitle.makeItGone()
        layout_seller.text_view_header1.makeItVisible()
        layout_seller.text_view_header1.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
        layout_seller.text_view_content1.makeItVisible()
        layout_seller.text_view_content1.setTextColor(ViewUtils.getColor(R.color.colorPrimary))
        layout_seller.text_view_header2.makeItVisible()
        layout_seller.text_view_header2.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
        layout_seller.text_view_content2.makeItVisible()
        layout_seller.text_view_content2.setTextColor(ViewUtils.getColor(R.color.colorPrimary))

        layout_biderator.text_view_title.setTextColor(ViewUtils.getColor(R.color.colorTextRegular))
        layout_biderator.text_view_subtitle.makeItGone()
        layout_biderator.text_view_header1.makeItVisible()
        layout_biderator.text_view_header1.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
        layout_biderator.text_view_content1.makeItVisible()
        layout_biderator.text_view_content1.setTextColor(ViewUtils.getColor(R.color.colorPrimary))
        layout_biderator.text_view_header2.makeItVisible()
        layout_biderator.text_view_header2.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
        layout_biderator.text_view_content2.makeItVisible()
        layout_biderator.text_view_content2.setTextColor(ViewUtils.getColor(R.color.colorPrimary))

        layout_consumer.text_view_title.setTextColor(ViewUtils.getColor(R.color.colorTextRegular))
        layout_consumer.text_view_subtitle.makeItGone()
        layout_consumer.text_view_header1.makeItVisible()
        layout_consumer.text_view_header1.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
        layout_consumer.text_view_content1.makeItVisible()
        layout_consumer.text_view_content1.setTextColor(ViewUtils.getColor(R.color.colorPrimary))
        layout_consumer.text_view_header2.makeItVisible()
        layout_consumer.text_view_header2.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
        layout_consumer.text_view_content2.makeItVisible()
        layout_consumer.text_view_content2.setTextColor(ViewUtils.getColor(R.color.colorPrimary))

        if (layout_seller.text_view_title.text == getString(R.string.my_rankings_seller)) {
            layout_seller.text_view_header2.makeItInvisible()
            layout_seller.text_view_content2.makeItInvisible()
        }

        layout_seller.image_view_resource.loadImage(R.drawable.ic_seller_white)
        layout_biderator.image_view_resource.loadImage(R.drawable.ic_biderator_white)
        layout_consumer.image_view_resource.loadImage(R.drawable.ic_consumer_white)

        layout_seller.text_view_header1.text = getString(R.string.dashboard_monthly)
        layout_biderator.text_view_header1.text = getString(R.string.dashboard_monthly)
        layout_consumer.text_view_header1.text = getString(R.string.dashboard_monthly)


        layout_seller.text_view_header2.text = getString(R.string.dashboard_yearly)
        layout_biderator.text_view_header2.text = getString(R.string.dashboard_yearly)
        layout_consumer.text_view_header2.text = getString(R.string.dashboard_yearly)

        val spinnerAdapter = ArrayAdapter.createFromResource(
            mContext!!,
            R.array.ranking_history,
            R.layout.spinner_custom_item1
        )

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_custom_dark_item1)
        spinner_history.adapter = spinnerAdapter

        initializeRecyclerView(
            recycler_view_history,
            MyRankingAdapter(this),
            null,
            null,
            LinearLayoutManager(mContext!!),
            null,
            null,
            null,
            null
        )
    }

    private fun setListeners() {
        spinner_history.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        presenter.getMySellerHistory(
                            mContext!!,
                            DataUtils.getInteger(R.integer.page_one)
                        )
                    }

                    1 -> {
                        presenter.getMyConsumerHistory(
                            mContext!!,
                            DataUtils.getInteger(R.integer.page_one)
                        )
                    }

                    else -> {
                        presenter.getMyBideratorHistory(
                            mContext!!,
                            DataUtils.getInteger(R.integer.page_one)
                        )
                    }
                }
            }
        }
    }

    private fun loadData() {
        presenter.getSoftwareListFromCloud(mContext!!)
        presenter.getMyRankings(mContext!!)
        presenter.getSoftwareListFromDatabase()
    }

    override fun stopUI() {

    }

    private fun getAdapter(): MyRankingAdapter {
        return recycler_view_history.adapter as MyRankingAdapter
    }

    override fun onGettingRankings(
        sellerMonthlyPoints: String,
        bideratorMonthlyPoints: String,
        bideratorYearlyPoints: String,
        consumerMonthlyPoints: String,
        consumerYearlyPoints: String
    ) {
        layout_seller.text_view_content1.text = sellerMonthlyPoints
        layout_biderator.text_view_content1.text = bideratorMonthlyPoints
        layout_biderator.text_view_content2.text = bideratorYearlyPoints
        layout_consumer.text_view_content1.text = consumerMonthlyPoints
        layout_consumer.text_view_content2.text = consumerYearlyPoints
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onGettingSellerHistory(list: List<MyRanking>) {
        if (list.isEmpty() || list[list.size - 1].currentPage == DataUtils.getInteger(R.integer.page_one)) {
            getAdapter().clear()
        }
        getAdapter().addItems(list)
        ProgressDialogUtils.on().hideProgressDialog()
    }

    override fun onGettingConsumerHistory(list: List<MyRanking>) {
        if (list.isEmpty() || list[list.size - 1].currentPage == DataUtils.getInteger(R.integer.page_one)) {
            getAdapter().clear()
        }
        getAdapter().addItems(list)
        ProgressDialogUtils.on().hideProgressDialog()
    }

    override fun onGettingBideratorHistory(list: List<MyRanking>) {
        if (list.isEmpty() || list[list.size - 1].currentPage == DataUtils.getInteger(R.integer.page_one)) {
            getAdapter().clear()
        }
        getAdapter().addItems(list)
        ProgressDialogUtils.on().hideProgressDialog()
    }

    override fun onGettingSoftwares(list: List<SoftwareEntity>) {
        getAdapter().softwareList = list
        getAdapter().notifyDataSetChanged()
    }

    override fun onNextPage(currentPage: Int) {
        when (spinner_history.selectedItemPosition) {
            0 -> {
                presenter.getMySellerHistory(mContext!!, (currentPage + 1))
            }

            1 -> {
                presenter.getMyConsumerHistory(mContext!!, (currentPage + 1))
            }

            else -> {
                presenter.getMyBideratorHistory(mContext!!, (currentPage + 1))
            }
        }
    }
}