package com.itechsoftsolutions.mtcore.main.ui.app.landing.container

import android.app.Activity
import android.content.Context
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.data.local.model.NavigationDrawerItem
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class ContainerPresenter : BasePresenter<ContainerMvpView>() {
    fun getDrawerMenuList(): List<NavigationDrawerItem> {
        val list: MutableList<NavigationDrawerItem> = ArrayList()

        list.add(
            NavigationDrawerItem(
                DataUtils.getString(R.string.navigation_drawer_dashboard),
                R.drawable.ic_dashboard
            )
        )
        list.add(
            NavigationDrawerItem(
                DataUtils.getString(R.string.navigation_drawer_my_wallets),
                R.drawable.ic_my_wallets
            )
        )
        list.add(
            NavigationDrawerItem(
                DataUtils.getString(R.string.navigation_drawer_my_referral),
                R.drawable.ic_my_referral
            )
        )
        list.add(
            NavigationDrawerItem(
                DataUtils.getString(R.string.navigation_drawer_my_rankings),
                R.drawable.ic_my_rankings
            )
        )
        list.add(
            NavigationDrawerItem(
                DataUtils.getString(R.string.navigation_drawer_ranking_list),
                R.drawable.ic_ranking_list
            )
        )

        list.add(
            NavigationDrawerItem(
                DataUtils.getString(R.string.navigation_drawer_software_licence),
                R.drawable.ic_software_licence
            )
        )
        list.add(
            NavigationDrawerItem(
                DataUtils.getString(R.string.navigation_drawer_go_to_bidstore),
                R.drawable.ic_go_to_bidstore
            )
        )
        list.add(
            NavigationDrawerItem(
                DataUtils.getString(R.string.navigation_drawer_my_profile),
                R.drawable.ic_my_profile
            )
        )
        list.add(
            NavigationDrawerItem(
                DataUtils.getString(R.string.navigation_drawer_settings),
                R.drawable.ic_settings
            )
        )
        list.add(
            NavigationDrawerItem(
                DataUtils.getString(R.string.dashboard_drawer_log_out),
                R.drawable.ic_log_out
            )
        )

        return list
    }

    fun getSoftwareList(context: Context) {
        compositeDisposable.add(
            BaseRepository.on().getSoftwareListFromCloud()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response != null && response.isSuccessful) {
                            compositeDisposable.add(
                                BaseRepository.on().insertSoftwaresToDatabase(
                                    response.data.collection
                                ).observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe({

                                    }, { e ->
                                        Timber.d(e)
                                    })
                            )
                        }
                    }
                }, {
                    Timber.d(it)
                })
        )
    }
}