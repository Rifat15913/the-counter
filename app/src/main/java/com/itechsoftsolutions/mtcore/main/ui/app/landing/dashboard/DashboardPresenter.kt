package com.itechsoftsolutions.mtcore.main.ui.app.landing.dashboard

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.BaseRepository
import com.itechsoftsolutions.mtcore.main.data.local.model.DashboardItem
import com.itechsoftsolutions.mtcore.main.ui.base.component.BasePresenter
import com.itechsoftsolutions.mtcore.main.ui.base.getString
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.helper.SharedPrefUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.HttpURLConnection

class DashboardPresenter : BasePresenter<DashboardMvpView>() {

    fun getDashboardData(context: Context) {
        ProgressDialogUtils.on().showProgressDialog(context)

        compositeDisposable.add(
            BaseRepository.on().getDashboardData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        compositeDisposable.add(BaseRepository.on().logOut(context as Activity))
                    } else {
                        val response = it.body()

                        if (response == null) {
                            mvpView?.onError(DataUtils.getString(R.string.dashboard_error_could_not_load_data))
                        } else {
                            if (response.isSuccessful) {
                                if (response.data.asJsonObject.has(Constants.JsonKeys.REFERRAL_LINK)
                                    && !(response.data.asJsonObject
                                        .get(Constants.JsonKeys.REFERRAL_LINK).isJsonNull)
                                ) {
                                    SharedPrefUtils.write(
                                        Constants.PreferenceKeys.REFERRAL_LINK,
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.REFERRAL_LINK).asString
                                    )
                                }

                                val list: MutableList<DashboardItem> = ArrayList()

                                if (response.data.asJsonObject.has(Constants.JsonKeys.AVAILABLE_MTR_BALANCE)
                                    && !(response.data.asJsonObject
                                        .get(Constants.JsonKeys.AVAILABLE_MTR_BALANCE).isJsonNull)
                                ) {
                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_available_mtr_balance),
                                            null,
                                            R.drawable.ic_mtr_balance,
                                            null,
                                            response.data.asJsonObject
                                                .get(Constants.JsonKeys.AVAILABLE_MTR_BALANCE).asString,
                                            null,
                                            null,
                                            null,
                                            null,
                                            DashboardItem.BALANCE_SECTION
                                        )
                                    )
                                }

                                if (response.data.asJsonObject.has(Constants.JsonKeys.AVAILABLE_BIDS_BALANCE)
                                    && !(response.data.asJsonObject
                                        .get(Constants.JsonKeys.AVAILABLE_BIDS_BALANCE).isJsonNull)
                                ) {
                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_available_bids),
                                            null,
                                            R.drawable.ic_bid_balance,
                                            null,
                                            response.data.asJsonObject
                                                .get(Constants.JsonKeys.AVAILABLE_BIDS_BALANCE).asString,
                                            null,
                                            null,
                                            null,
                                            null,
                                            DashboardItem.BALANCE_SECTION
                                        )
                                    )
                                }

                                if (response.data.asJsonObject.has(Constants.JsonKeys.AVAILABLE_POINTS_BALANCE)
                                    && !(response.data.asJsonObject
                                        .get(Constants.JsonKeys.AVAILABLE_POINTS_BALANCE).isJsonNull)
                                ) {
                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_available_points),
                                            null,
                                            R.drawable.ic_point_balance,
                                            null,
                                            response.data.asJsonObject
                                                .get(Constants.JsonKeys.AVAILABLE_POINTS_BALANCE).asString,
                                            null,
                                            null,
                                            null,
                                            null,
                                            DashboardItem.BALANCE_SECTION
                                        )
                                    )
                                }

                                if (response.data.asJsonObject.has(Constants.JsonKeys.PENDING_WITHDRAWALS)
                                    && !(response.data.asJsonObject
                                        .get(Constants.JsonKeys.PENDING_WITHDRAWALS).isJsonNull)
                                ) {
                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_available_pending_withdrawals),
                                            null,
                                            R.drawable.ic_mtr_balance,
                                            null,
                                            response.data.asJsonObject
                                                .get(Constants.JsonKeys.PENDING_WITHDRAWALS).asString,
                                            null,
                                            null,
                                            null,
                                            null,
                                            DashboardItem.BALANCE_SECTION
                                        )
                                    )
                                }

                                if (response.data.asJsonObject.has(Constants.JsonKeys.REFERRAL_LINK)
                                    && !(response.data.asJsonObject
                                        .get(Constants.JsonKeys.REFERRAL_LINK).isJsonNull)
                                ) {
                                    list.add(
                                        DashboardItem(
                                            null,
                                            null,
                                            null,
                                            null,
                                            response.data.asJsonObject
                                                .get(Constants.JsonKeys.REFERRAL_LINK).asString,
                                            null,
                                            null,
                                            null,
                                            null,
                                            DashboardItem.SHARE_SECTION
                                        )
                                    )
                                }

                                list.add(
                                    DashboardItem(
                                        getString(R.string.dashboard_shark_club),
                                        null,
                                        null,
                                        null,
                                        response.data
                                            .asJsonObject[Constants.JsonKeys.SHARK_CLUB]
                                            .asString,
                                        null,
                                        null,
                                        null,
                                        null,
                                        DashboardItem.MY_COMPONENT_SECTION
                                    )
                                )

                                val softwareNameList: MutableList<String> = ArrayList()

                                if (response.data
                                        .asJsonObject[Constants.JsonKeys.SOFTWARES]
                                        .isJsonNull
                                ) {
                                    softwareNameList.add(getString(R.string.dashboard_no_data_available))
                                } else {
                                    response.data
                                        .asJsonObject[Constants.JsonKeys.SOFTWARES]
                                        .asJsonArray
                                        .forEach { element ->
                                            softwareNameList.add(element.asString)
                                        }
                                }

                                list.add(
                                    DashboardItem(
                                        getString(R.string.dashboard_my_softwares),
                                        null,
                                        null,
                                        null,
                                        softwareNameList.joinToString("\n"),
                                        null,
                                        null,
                                        null,
                                        null,
                                        DashboardItem.MY_COMPONENT_SECTION
                                    )
                                )

                                list.add(
                                    DashboardItem(
                                        getString(R.string.dashboard_my_referral),
                                        null,
                                        null,
                                        null,
                                        response.data
                                            .asJsonObject[Constants.JsonKeys.CHILDREN_NUMBER]
                                            .asString,
                                        null,
                                        null,
                                        null,
                                        null,
                                        DashboardItem.MY_COMPONENT_SECTION
                                    )
                                )

                                if (response.data.asJsonObject.has(Constants.JsonKeys.MONTHLY)
                                    && !(response.data.asJsonObject.get(Constants.JsonKeys.MONTHLY).isJsonNull)
                                    && response.data.asJsonObject.has(Constants.JsonKeys.YEARLY)
                                    && !(response.data.asJsonObject.get(Constants.JsonKeys.YEARLY).isJsonNull)
                                ) {

                                    val monthlyObject =
                                        response.data.asJsonObject.get(Constants.JsonKeys.MONTHLY)
                                    val yearlyObject =
                                        response.data.asJsonObject.get(Constants.JsonKeys.YEARLY)

                                    if (monthlyObject.asJsonObject.has(Constants.JsonKeys.SELLER)
                                        && !(monthlyObject.asJsonObject.get(Constants.JsonKeys.SELLER).isJsonNull)
                                        && yearlyObject.asJsonObject.has(Constants.JsonKeys.SELLER)
                                        && !(yearlyObject.asJsonObject.get(Constants.JsonKeys.SELLER).isJsonNull)
                                    ) {
                                        list.add(
                                            DashboardItem(
                                                getString(R.string.dashboard_top_seller),
                                                null,
                                                R.drawable.ic_seller_white,
                                                getString(R.string.dashboard_yearly),
                                                yearlyObject.asJsonObject.get(Constants.JsonKeys.SELLER).asString,
                                                getString(R.string.dashboard_monthly),
                                                monthlyObject.asJsonObject.get(Constants.JsonKeys.SELLER).asString,
                                                null,
                                                null,
                                                DashboardItem.TOP_REWARD_SECTION
                                            )
                                        )
                                    }

                                    if (monthlyObject.asJsonObject.has(Constants.JsonKeys.CONSUMER)
                                        && !(monthlyObject.asJsonObject.get(Constants.JsonKeys.CONSUMER).isJsonNull)
                                        && yearlyObject.asJsonObject.has(Constants.JsonKeys.CONSUMER)
                                        && !(yearlyObject.asJsonObject.get(Constants.JsonKeys.CONSUMER).isJsonNull)
                                    ) {
                                        list.add(
                                            DashboardItem(
                                                getString(R.string.dashboard_top_consumer),
                                                null,
                                                R.drawable.ic_consumer_white,
                                                getString(R.string.dashboard_yearly),
                                                yearlyObject.asJsonObject.get(Constants.JsonKeys.CONSUMER).asString,
                                                getString(R.string.dashboard_monthly),
                                                monthlyObject.asJsonObject.get(Constants.JsonKeys.CONSUMER).asString,
                                                null,
                                                null,
                                                DashboardItem.TOP_REWARD_SECTION
                                            )
                                        )
                                    }

                                    if (monthlyObject.asJsonObject.has(Constants.JsonKeys.BIDERATOR)
                                        && !(monthlyObject.asJsonObject.get(Constants.JsonKeys.BIDERATOR).isJsonNull)
                                        && yearlyObject.asJsonObject.has(Constants.JsonKeys.BIDERATOR)
                                        && !(yearlyObject.asJsonObject.get(Constants.JsonKeys.BIDERATOR).isJsonNull)
                                    ) {
                                        list.add(
                                            DashboardItem(
                                                getString(R.string.dashboard_top_biderator),
                                                null,
                                                R.drawable.ic_biderator_white,
                                                getString(R.string.dashboard_yearly),
                                                yearlyObject.asJsonObject.get(Constants.JsonKeys.BIDERATOR).asString,
                                                getString(R.string.dashboard_monthly),
                                                monthlyObject.asJsonObject.get(Constants.JsonKeys.BIDERATOR).asString,
                                                null,
                                                null,
                                                DashboardItem.TOP_REWARD_SECTION
                                            )
                                        )
                                    }
                                }

                                if (response.data.asJsonObject.has(Constants.JsonKeys.MONTHLY_WINNERS)
                                    && !(response.data.asJsonObject.get(Constants.JsonKeys.MONTHLY_WINNERS).isJsonNull)
                                ) {

                                    val monthlyWinnersObject =
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.MONTHLY_WINNERS)

                                    val sellerObject =
                                        monthlyWinnersObject.asJsonObject
                                            .get(Constants.JsonKeys.SELLER)

                                    val consumerObject =
                                        monthlyWinnersObject.asJsonObject
                                            .get(Constants.JsonKeys.CONSUMER)

                                    val bideratorObject =
                                        monthlyWinnersObject.asJsonObject
                                            .get(Constants.JsonKeys.BIDERATOR)

                                    val luckyObject =
                                        monthlyWinnersObject.asJsonObject
                                            .get(Constants.JsonKeys.LUCKY)

                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_last_month_top_seller),
                                            sellerObject.asJsonObject.get(Constants.JsonKeys.NAME).asString,
                                            R.drawable.ic_seller_brown,
                                            getString(R.string.dashboard_points),
                                            sellerObject.asJsonObject.get(Constants.JsonKeys.POINTS).asString,
                                            getString(R.string.dashboard_reward),
                                            sellerObject.asJsonObject.get(Constants.JsonKeys.REWARD).asString,
                                            null,
                                            null,
                                            DashboardItem.LAST_MONTH_SECTION
                                        )
                                    )

                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_last_month_top_consumer),
                                            consumerObject.asJsonObject.get(Constants.JsonKeys.NAME).asString,
                                            R.drawable.ic_consumer_brown,
                                            getString(R.string.dashboard_points),
                                            consumerObject.asJsonObject.get(Constants.JsonKeys.POINTS).asString,
                                            getString(R.string.dashboard_reward),
                                            consumerObject.asJsonObject.get(Constants.JsonKeys.REWARD).asString,
                                            null,
                                            null,
                                            DashboardItem.LAST_MONTH_SECTION
                                        )
                                    )

                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_last_month_top_biderator),
                                            bideratorObject.asJsonObject.get(Constants.JsonKeys.NAME).asString,
                                            R.drawable.ic_biderator_brown,
                                            getString(R.string.dashboard_points),
                                            bideratorObject.asJsonObject.get(Constants.JsonKeys.POINTS).asString,
                                            getString(R.string.dashboard_reward),
                                            bideratorObject.asJsonObject.get(Constants.JsonKeys.REWARD).asString,
                                            null,
                                            null,
                                            DashboardItem.LAST_MONTH_SECTION
                                        )
                                    )

                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_last_month_top_lucky),
                                            luckyObject.asJsonObject.get(Constants.JsonKeys.NAME).asString,
                                            R.drawable.ic_lucky_brown,
                                            getString(R.string.dashboard_points),
                                            luckyObject.asJsonObject.get(Constants.JsonKeys.POINTS).asString,
                                            getString(R.string.dashboard_reward),
                                            luckyObject.asJsonObject.get(Constants.JsonKeys.REWARD).asString,
                                            null,
                                            null,
                                            DashboardItem.LAST_MONTH_SECTION
                                        )
                                    )
                                }

                                if (response.data.asJsonObject.has(Constants.JsonKeys.YEARLY_WINNERS)
                                    && !(response.data.asJsonObject.get(Constants.JsonKeys.YEARLY_WINNERS).isJsonNull)
                                ) {

                                    val yearlyWinnersObject =
                                        response.data.asJsonObject
                                            .get(Constants.JsonKeys.YEARLY_WINNERS)

                                    val sellerObject =
                                        yearlyWinnersObject.asJsonObject
                                            .get(Constants.JsonKeys.SELLER)

                                    val consumerObject =
                                        yearlyWinnersObject.asJsonObject
                                            .get(Constants.JsonKeys.CONSUMER)

                                    val bideratorObject =
                                        yearlyWinnersObject.asJsonObject
                                            .get(Constants.JsonKeys.BIDERATOR)

                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_last_year_top_seller),
                                            sellerObject.asJsonObject.get(Constants.JsonKeys.NAME).asString,
                                            R.drawable.ic_seller_black,
                                            getString(R.string.dashboard_points),
                                            sellerObject.asJsonObject.get(Constants.JsonKeys.POINTS).asString,
                                            getString(R.string.dashboard_reward),
                                            sellerObject.asJsonObject.get(Constants.JsonKeys.REWARD).asString,
                                            null,
                                            null,
                                            DashboardItem.LAST_YEAR_SECTION
                                        )
                                    )

                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_last_year_top_consumer),
                                            consumerObject.asJsonObject.get(Constants.JsonKeys.NAME).asString,
                                            R.drawable.ic_consumer_black,
                                            getString(R.string.dashboard_points),
                                            consumerObject.asJsonObject.get(Constants.JsonKeys.POINTS).asString,
                                            getString(R.string.dashboard_reward),
                                            consumerObject.asJsonObject.get(Constants.JsonKeys.REWARD).asString,
                                            null,
                                            null,
                                            DashboardItem.LAST_YEAR_SECTION
                                        )
                                    )

                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_last_year_top_biderator),
                                            bideratorObject.asJsonObject.get(Constants.JsonKeys.NAME).asString,
                                            R.drawable.ic_biderator_black,
                                            getString(R.string.dashboard_points),
                                            bideratorObject.asJsonObject.get(Constants.JsonKeys.POINTS).asString,
                                            getString(R.string.dashboard_reward),
                                            bideratorObject.asJsonObject.get(Constants.JsonKeys.REWARD).asString,
                                            null,
                                            null,
                                            DashboardItem.LAST_YEAR_SECTION
                                        )
                                    )
                                }

                                if (response.data
                                        .asJsonObject
                                        .has(Constants.JsonKeys.BIDERATION_STATUS)
                                    && !(response.data
                                        .asJsonObject
                                        .get(Constants.JsonKeys.BIDERATION_STATUS)
                                        .isJsonNull)
                                ) {
                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_bids_status),
                                            null,
                                            R.drawable.ic_seller_black,
                                            getString(R.string.dashboard_total_sold),
                                            response.data
                                                .asJsonObject[Constants.JsonKeys.BIDERATION_STATUS]
                                                .asJsonObject[Constants.JsonKeys.TOTAL_SOLD]
                                                .asString,
                                            getString(R.string.dashboard_total_biderated),
                                            response.data
                                                .asJsonObject[Constants.JsonKeys.BIDERATION_STATUS]
                                                .asJsonObject[Constants.JsonKeys.TOTAL_BIDERATED]
                                                .asString,
                                            getString(R.string.dashboard_in_user_wallets),
                                            response.data
                                                .asJsonObject[Constants.JsonKeys.BIDERATION_STATUS]
                                                .asJsonObject[Constants.JsonKeys.IN_USER_WALLETS]
                                                .asString,
                                            DashboardItem.STATUS_SECTION
                                        )
                                    )

                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_mtr_status),
                                            null,
                                            R.drawable.ic_seller_black,
                                            getString(R.string.dashboard_total_safe),
                                            response.data
                                                .asJsonObject[Constants.JsonKeys.BIDERATION_STATUS]
                                                .asJsonObject[Constants.JsonKeys.TOTAL_SAFE]
                                                .asString,
                                            getString(R.string.dashboard_total_distributed),
                                            response.data
                                                .asJsonObject[Constants.JsonKeys.BIDERATION_STATUS]
                                                .asJsonObject[Constants.JsonKeys.TOTAL_DISTRIBUTED]
                                                .asString,
                                            null,
                                            null,
                                            DashboardItem.STATUS_SECTION
                                        )
                                    )

                                    list.add(
                                        DashboardItem(
                                            getString(R.string.dashboard_bideration_status),
                                            null,
                                            R.drawable.ic_seller_black,
                                            getString(R.string.dashboard_total_bideration_blocks),
                                            response.data
                                                .asJsonObject[Constants.JsonKeys.BIDERATION_STATUS]
                                                .asJsonObject[Constants.JsonKeys.TOTAL_BIDERATED_BLOCKS]
                                                .asString,
                                            getString(R.string.dashboard_total_distributed_mtr_per_day),
                                            response.data
                                                .asJsonObject[Constants.JsonKeys.BIDERATION_STATUS]
                                                .asJsonObject[Constants.JsonKeys.TOTAL_BIDERATED_PER_DAY]
                                                .asString,
                                            null,
                                            null,
                                            DashboardItem.STATUS_SECTION
                                        )
                                    )
                                }

                                mvpView?.onSuccess(list)
                            } else {
                                mvpView?.onError(response.message)
                            }
                        }
                    }
                }, {
                    Timber.d(it)

                    if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                        mvpView?.onError(it.message)
                    } else {
                        mvpView?.onError(DataUtils.getString(R.string.dashboard_error_could_not_load_data))
                    }

                })
        )
    }

    fun copyTextToClipboard(context: Context, text: String) {
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        clipboard?.primaryClip =
            ClipData.newPlainText(Constants.PreferenceKeys.REFERRAL_LINK, text)
        ToastUtils.nativeShort(getString(R.string.copied_to_clipboard))
    }
}