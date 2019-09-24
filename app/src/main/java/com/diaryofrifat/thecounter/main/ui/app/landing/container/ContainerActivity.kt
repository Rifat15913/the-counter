package com.diaryofrifat.thecounter.main.ui.app.landing.container

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.BaseRepository
import com.diaryofrifat.thecounter.main.data.local.model.NavigationDrawerItem
import com.diaryofrifat.thecounter.main.ui.app.landing.dashboard.DashboardFragment
import com.diaryofrifat.thecounter.main.ui.app.myreferral.MyReferralFragment
import com.diaryofrifat.thecounter.main.ui.app.profile.container.ProfileContainerFragment
import com.diaryofrifat.thecounter.main.ui.app.ranking.myranking.MyRankingFragment
import com.diaryofrifat.thecounter.main.ui.app.ranking.rankinglist.RankingListFragment
import com.diaryofrifat.thecounter.main.ui.app.settings.container.SettingsFragment
import com.diaryofrifat.thecounter.main.ui.app.software.container.SoftwareLicenceContainerFragment
import com.diaryofrifat.thecounter.main.ui.app.wallets.mywallets.MyWalletsFragment
import com.diaryofrifat.thecounter.main.ui.base.callback.ItemClickListener
import com.diaryofrifat.thecounter.main.ui.base.component.BaseActivity
import com.diaryofrifat.thecounter.main.ui.base.helper.AlertDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.helper.LinearMarginItemDecoration
import com.diaryofrifat.thecounter.main.ui.base.makeItGone
import com.diaryofrifat.thecounter.main.ui.base.makeItVisible
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.LanguageUtils
import com.diaryofrifat.thecounter.utils.helper.ViewUtils
import kotlinx.android.synthetic.main.activity_container.*


class ContainerActivity : BaseActivity<ContainerMvpView, ContainerPresenter>() {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, ContainerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            runCurrentActivity(context, intent)
        }
    }

    private lateinit var mBinding: com.diaryofrifat.thecounter.databinding.ActivityContainerBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_container

    override fun getActivityPresenter(): ContainerPresenter {
        return ContainerPresenter()
    }

    override fun startUI() {
        mBinding =
            viewDataBinding as com.diaryofrifat.thecounter.databinding.ActivityContainerBinding

        setListeners()
        initialize()
        loadData()
    }

    override fun stopUI() {

    }

    override fun onBackPressed() {
        drawer_layout_whole_container.closeDrawers()

        if (currentFragment is DashboardFragment) {
            finish()
            super.onBackPressed()
        } else {
            visitDashboard()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.image_view_drawer_closer -> {
                mBinding.drawerLayoutWholeContainer.closeDrawers()
            }

            R.id.image_view_drawer_opener -> {
                mBinding.drawerLayoutWholeContainer.openDrawer(GravityCompat.START)
            }

            R.id.image_view_ranking_list -> {
                image_view_ranking_list.makeItGone()
                visitRankingList()
            }
        }
    }

    private fun loadData() {
        presenter.getSoftwareList(this)
    }

    private fun setListeners() {
        setClickListener(
            mBinding.imageViewDrawerOpener,
            mBinding.navigationView.imageViewDrawerCloser,
            image_view_ranking_list
        )
    }

    private fun initialize() {
        ViewUtils.initializeRecyclerView(
            getDrawerRecyclerView(),
            DrawerAdapter(),
            object : ItemClickListener<NavigationDrawerItem> {
                override fun onItemClick(view: View, item: NavigationDrawerItem) {
                    image_view_ranking_list.makeItGone()
                    mBinding.drawerLayoutWholeContainer.closeDrawers()

                    when (item.resourceId) {
                        R.drawable.ic_dashboard -> {
                            visitDashboard()
                        }

                        R.drawable.ic_my_wallets -> {
                            visitMyWallets()
                        }

                        R.drawable.ic_my_referral -> {
                            visitMyReferral()
                        }

                        R.drawable.ic_go_to_bidstore -> {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(getString(R.string.url_bidstore))
                            startActivity(intent)
                        }

                        R.drawable.ic_my_rankings -> {
                            image_view_ranking_list.makeItVisible()
                            visitMyRankings()
                        }

                        R.drawable.ic_ranking_list -> {
                            visitRankingList()
                        }

                        R.drawable.ic_software_licence -> {
                            visitSoftwareLicense()
                        }

                        R.drawable.ic_settings -> {
                            visitSettings()
                        }

                        R.drawable.ic_my_profile -> {
                            visitMyProfile()
                        }

                        R.drawable.ic_language -> {
                            LanguageUtils.setLanguageAndRestartApplication(
                                Intent(this@ContainerActivity, ContainerActivity::class.java),
                                this@ContainerActivity,
                                if (item.title == getString(R.string.dashboard_drawer_english)) {
                                    Constants.LanguageCodes.ENGLISH
                                } else {
                                    Constants.LanguageCodes.PORTUGUESE
                                }
                            )
                        }

                        R.drawable.ic_log_out -> {
                            AlertDialogUtils.on().showNativeDialog(
                                this@ContainerActivity,
                                true,
                                getString(R.string.yes),
                                DialogInterface.OnClickListener { dialog, _ ->
                                    dialog.dismiss()
                                    presenter.compositeDisposable.add(
                                        BaseRepository.on().logOut(this@ContainerActivity, true)
                                    )
                                },
                                getString(R.string.no),
                                DialogInterface.OnClickListener { dialog, _ ->
                                    dialog.dismiss()
                                },
                                getString(R.string.are_you_sure),
                                null,
                                null
                            )
                        }
                    }
                }
            },
            null,
            LinearLayoutManager(this),
            LinearMarginItemDecoration(
                ViewUtils.getPixel(R.dimen.margin_16),
                ViewUtils.getPixel(R.dimen.margin_16),
                ViewUtils.getPixel(R.dimen.margin_16),
                ViewUtils.getPixel(R.dimen.margin_16)
            ),
            null,
            null
        )

        mBinding.navigationViewContainer.background = null
        getAdapter().clear()
        getAdapter().addItems(presenter.getDrawerMenuList())

        visitDashboard()
    }

    fun visitSoftwareLicense() {
        commitFragment(
            R.id.constraint_layout_full_fragment_container,
            SoftwareLicenceContainerFragment()
        )
    }

    private fun visitMyWallets() {
        commitFragment(
            R.id.constraint_layout_full_fragment_container,
            MyWalletsFragment()
        )
    }

    private fun getDrawerRecyclerView(): RecyclerView {
        return mBinding.navigationView.recyclerViewMenu
    }

    private fun getAdapter(): DrawerAdapter {
        return getDrawerRecyclerView().adapter as DrawerAdapter
    }

    fun setPageTitle(title: String) {
        text_view_title.text = title
    }

    fun visitDashboard() {
        commitFragment(R.id.constraint_layout_full_fragment_container, DashboardFragment())
    }

    fun visitMyReferral() {
        commitFragment(R.id.constraint_layout_full_fragment_container, MyReferralFragment())
    }

    fun visitSettings() {
        commitFragment(
            R.id.constraint_layout_full_fragment_container,
            SettingsFragment()
        )
    }

    fun visitMyProfile() {
        commitFragment(
            R.id.constraint_layout_full_fragment_container,
            ProfileContainerFragment()
        )
    }

    fun visitRankingList() {
        commitFragment(R.id.constraint_layout_full_fragment_container, RankingListFragment())
    }

    fun visitMyRankings() {
        commitFragment(R.id.constraint_layout_full_fragment_container, MyRankingFragment())
    }
}