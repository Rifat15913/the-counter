package com.itechsoftsolutions.mtcore.main.ui.app.ranking.rankinglist

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.remote.model.Ranker
import com.itechsoftsolutions.mtcore.main.ui.app.landing.container.ContainerActivity
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.initializeRecyclerView
import com.itechsoftsolutions.mtcore.main.ui.base.toTitleCase
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_ranking_list.*

class RankingListFragment : BaseFragment<RankingListMvpView, RankingListPresenter>(),
    RankingListMvpView {

    private val actorTypeItemSelectListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            setupSpinner(
                spinner_session,
                if (position == 0)
                    R.array.ranking_session_seller
                else
                    R.array.ranking_session_others,
                sessionItemSelectListener
            )

            presenter.updateRankerList(spinner_session.selectedItemPosition, position)
        }
    }

    private val sessionItemSelectListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            presenter.updateRankerList(position, spinner_actor_type.selectedItemPosition)
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_ranking_list

    override fun getFragmentPresenter(): RankingListPresenter {
        return RankingListPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
        loadData()
    }

    private fun initialize() {
        (activity as ContainerActivity).setPageTitle(getString(R.string.ranking_list_title).toTitleCase())

        setupSpinner(spinner_actor_type, R.array.ranking_actor_types, actorTypeItemSelectListener)

        initializeRecyclerView(
            recycler_view_ranking,
            RankingListAdapter(),
            null,
            null,
            LinearLayoutManager(mContext!!),
            null,
            null,
            null,
            null
        )
    }

    private fun setupSpinner(
        spinner: AppCompatSpinner, arrayResourceID: Int,
        onItemSelectedListener: AdapterView.OnItemSelectedListener
    ) {
        val adapter = ArrayAdapter.createFromResource(
            mContext!!,
            arrayResourceID,
            R.layout.spinner_custom_item2
        )

        adapter.setDropDownViewResource(R.layout.spinner_custom_dark_item2)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = onItemSelectedListener
    }

    private fun setListeners() {

    }

    private fun loadData() {
        presenter.getRankingList(mContext!!)
    }

    override fun stopUI() {

    }

    private fun getAdapter(): RankingListAdapter {
        return recycler_view_ranking.adapter as RankingListAdapter
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onGettingData(list: List<Ranker>) {
        getAdapter().clear()
        getAdapter().addItems(list)
        ProgressDialogUtils.on().hideProgressDialog()
    }
}