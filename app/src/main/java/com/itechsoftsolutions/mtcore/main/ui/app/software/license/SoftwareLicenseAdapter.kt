package com.itechsoftsolutions.mtcore.main.ui.app.software.license

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareStatusEntity
import com.itechsoftsolutions.mtcore.main.ui.base.*
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseAdapter
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseViewHolder
import com.itechsoftsolutions.mtcore.utils.helper.TimeUtils
import kotlinx.android.synthetic.main.item_pricing_panel.view.*
import java.util.*

class SoftwareLicenseAdapter : BaseAdapter<SoftwareStatusEntity>() {
    override fun isEqual(left: SoftwareStatusEntity, right: SoftwareStatusEntity): Boolean {
        return left.softwareID == right.softwareID
    }

    override fun newViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SoftwareStatusEntity> {
        return SoftwareViewHolder(inflate(parent, R.layout.item_pricing_panel))
    }

    inner class SoftwareViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<SoftwareStatusEntity>(viewDataBinding) {

        override fun bind(item: SoftwareStatusEntity) {
            setClickListener(itemView.text_view_action)

            itemView.text_view_title.text = item.title

            itemView.text_view_price.text =
                String.format(
                    Locale.getDefault(),
                    getString(R.string.software_license_price_yearly),
                    item.pricePerYear.toString()
                )

            itemView.text_view_speed.text =
                String.format(
                    Locale.getDefault(),
                    getString(R.string.software_license_speed),
                    item.speedPerSecond.toString()
                )

            itemView.text_view_ranking_points.text =
                String.format(
                    Locale.getDefault(),
                    getString(R.string.software_license_ranking_points),
                    item.cores.toString()
                )

            itemView.text_view_consumer_ranking_points.text =
                String.format(
                    Locale.getDefault(),
                    getString(R.string.software_license_consumer_ranking_points),
                    item.consumerRankingPoints.toString()
                )

            when (item.status) {
                SoftwareStatusEntity.ACTIVE -> {
                    itemView.text_view_action.text =
                        getString(R.string.software_license_active).toTitleCase()

                    itemView.view_separator.makeItVisible()
                    itemView.text_view_starting_date.makeItVisible()
                    itemView.text_view_ending_date.makeItVisible()
                    itemView.text_view_day_amount.makeItVisible()
                    itemView.text_view_days_left.makeItVisible()

                    if (!TextUtils.isEmpty(item.startingDate)) {
                        itemView.text_view_starting_date.text =
                            String.format(
                                Locale.getDefault(),
                                getString(R.string.software_license_start_date),
                                TimeUtils.getFormattedOnlyDateString(
                                    TimeUtils.getCalendarFromDate(item.startingDate!!)
                                        .timeInMillis
                                )
                            )
                    }

                    if (!TextUtils.isEmpty(item.endingDate)) {
                        itemView.text_view_ending_date.text =
                            String.format(
                                Locale.getDefault(),
                                getString(R.string.software_license_end_date),
                                TimeUtils.getFormattedOnlyDateString(
                                    TimeUtils.getCalendarFromDate(item.endingDate!!)
                                        .timeInMillis
                                )
                            )

                        itemView.text_view_day_amount.text =
                            TimeUtils.convertMillisecondsToDays(
                                TimeUtils.getCalendarFromDate(item.endingDate!!)
                                    .timeInMillis -
                                        TimeUtils.currentTime()
                            ).toString()
                    }

                    itemView.text_view_action.isEnabled = false
                }

                SoftwareStatusEntity.BANK_PENDING -> {
                    itemView.text_view_action.text =
                        getString(R.string.software_license_upload_bank_receipt).toTitleCase()

                    itemView.view_separator.makeItGone()
                    itemView.text_view_starting_date.makeItGone()
                    itemView.text_view_ending_date.makeItGone()
                    itemView.text_view_day_amount.makeItGone()
                    itemView.text_view_days_left.makeItGone()

                    itemView.text_view_action.isEnabled = true
                }

                SoftwareStatusEntity.ADMIN_PENDING -> {
                    itemView.text_view_action.text =
                        getString(R.string.software_license_pending).toTitleCase()

                    itemView.view_separator.makeItGone()
                    itemView.text_view_starting_date.makeItGone()
                    itemView.text_view_ending_date.makeItGone()
                    itemView.text_view_day_amount.makeItGone()
                    itemView.text_view_days_left.makeItGone()

                    itemView.text_view_action.isEnabled = false
                }

                else -> {
                    itemView.text_view_action.text =
                        getString(R.string.software_license_buy_now).toTitleCase()

                    itemView.view_separator.makeItGone()
                    itemView.text_view_starting_date.makeItGone()
                    itemView.text_view_ending_date.makeItGone()
                    itemView.text_view_day_amount.makeItGone()
                    itemView.text_view_days_left.makeItGone()

                    itemView.text_view_action.isEnabled = true
                }
            }

            itemView.text_view_action.setRipple(R.color.colorWhite50)
        }

        override fun onClick(view: View) {
            super.onClick(view)

            getItem(adapterPosition)?.let {
                mItemClickListener?.onItemClick(view, it)
            }
        }
    }
}