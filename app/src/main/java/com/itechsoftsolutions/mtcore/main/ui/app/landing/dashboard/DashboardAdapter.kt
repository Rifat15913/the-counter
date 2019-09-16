package com.itechsoftsolutions.mtcore.main.ui.app.landing.dashboard

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.local.model.DashboardItem
import com.itechsoftsolutions.mtcore.main.ui.base.*
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseAdapter
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseViewHolder
import com.itechsoftsolutions.mtcore.utils.helper.ViewUtils
import kotlinx.android.synthetic.main.item_dashboard_landscape_3_headers.view.*
import kotlinx.android.synthetic.main.item_dashboard_portrait.view.*
import kotlinx.android.synthetic.main.item_dashboard_portrait.view.constraint_layout_main_container
import kotlinx.android.synthetic.main.item_dashboard_portrait.view.image_view_resource
import kotlinx.android.synthetic.main.item_dashboard_portrait.view.text_view_content1
import kotlinx.android.synthetic.main.item_dashboard_portrait.view.text_view_content2
import kotlinx.android.synthetic.main.item_dashboard_portrait.view.text_view_header1
import kotlinx.android.synthetic.main.item_dashboard_portrait.view.text_view_header2
import kotlinx.android.synthetic.main.item_dashboard_portrait.view.text_view_title
import kotlinx.android.synthetic.main.item_share_my_referral.view.*

class DashboardAdapter : BaseAdapter<DashboardItem>() {
    override fun isEqual(left: DashboardItem, right: DashboardItem): Boolean {
        return false
    }

    override fun newViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DashboardItem> {
        return when (viewType) {
            DashboardItem.SHARE_SECTION -> {
                DashboardItemViewHolder(inflate(parent, R.layout.item_share_my_referral))
            }

            DashboardItem.LAST_YEAR_SECTION -> {
                DashboardItemViewHolder(
                    inflate(
                        parent,
                        R.layout.item_dashboard_landscape_2_headers
                    )
                )
            }

            DashboardItem.STATUS_SECTION -> {
                DashboardItemViewHolder(
                    inflate(
                        parent,
                        R.layout.item_dashboard_landscape_3_headers
                    )
                )
            }

            else -> {
                DashboardItemViewHolder(inflate(parent, R.layout.item_dashboard_portrait))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.let {
            return it.itemSection
        }

        return 0
    }

    inner class DashboardItemViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<DashboardItem>(viewDataBinding) {

        override fun bind(item: DashboardItem) {
            when (item.itemSection) {
                DashboardItem.BALANCE_SECTION -> {
                    itemView.constraint_layout_main_container.setBackgroundColor(
                        ViewUtils.getColor(
                            R.color.colorWhite
                        )
                    )
                    itemView.text_view_title.setTextColor(ViewUtils.getColor(R.color.colorTextPureBlack))
                    itemView.text_view_subtitle.makeItGone()
                    itemView.text_view_header1.makeItGone()
                    itemView.text_view_content1.makeItVisible()
                    itemView.text_view_content1.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
                    itemView.text_view_header2.makeItGone()
                    itemView.text_view_content2.makeItGone()

                    itemView.text_view_title.text = item.title
                    itemView.text_view_content1.text = item.content1
                    itemView.image_view_resource.loadImage(item.resource!!)
                }

                DashboardItem.SHARE_SECTION -> {
                    itemView.image_view_copy_url.setRipple(R.color.colorWhite50)
                    itemView.image_view_facebook.setRipple(R.color.colorWhite50)
                    itemView.image_view_twitter.setRipple(R.color.colorWhite50)

                    setClickListener(
                        itemView.image_view_copy_url,
                        itemView.image_view_facebook,
                        itemView.image_view_twitter
                    )
                }

                DashboardItem.MY_COMPONENT_SECTION -> {
                    itemView.constraint_layout_main_container
                        .setBackgroundColor(ViewUtils.getColor(R.color.colorDashboardItemDarkBackground))
                    itemView.text_view_title.setTextColor(ViewUtils.getColor(R.color.colorTextRegular))
                    itemView.text_view_subtitle.makeItGone()
                    itemView.text_view_header1.makeItInvisible()
                    itemView.text_view_content1.makeItVisible()
                    itemView.text_view_content1.setTextColor(ViewUtils.getColor(R.color.colorPrimary))
                    itemView.text_view_header2.makeItGone()
                    itemView.text_view_content2.makeItGone()
                    itemView.image_view_resource.makeItGone()

                    itemView.text_view_title.text = item.title
                    itemView.text_view_content1.text = item.content1
                }

                DashboardItem.TOP_REWARD_SECTION -> {
                    itemView.constraint_layout_main_container
                        .setBackgroundColor(ViewUtils.getColor(R.color.colorDashboardItemDarkBackground))
                    itemView.text_view_title.setTextColor(ViewUtils.getColor(R.color.colorTextRegular))
                    itemView.text_view_subtitle.makeItGone()
                    itemView.text_view_header1.makeItVisible()
                    itemView.text_view_header1.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
                    itemView.text_view_content1.makeItVisible()
                    itemView.text_view_content1.setTextColor(ViewUtils.getColor(R.color.colorPrimary))
                    itemView.text_view_header2.makeItVisible()
                    itemView.text_view_header2.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
                    itemView.text_view_content2.makeItVisible()
                    itemView.text_view_content2.setTextColor(ViewUtils.getColor(R.color.colorPrimary))

                    if (item.title == getString(R.string.dashboard_top_seller)) {
                        itemView.text_view_header1.makeItInvisible()
                        itemView.text_view_content1.makeItInvisible()
                    }

                    itemView.text_view_title.text = item.title
                    itemView.text_view_header1.text = item.header1
                    itemView.text_view_content1.text = item.content1
                    itemView.text_view_header2.text = item.header2
                    itemView.text_view_content2.text = item.content2

                    item.resource?.let {
                        itemView.image_view_resource.loadImage(it)
                    }
                }

                DashboardItem.LAST_MONTH_SECTION -> {
                    itemView.constraint_layout_main_container
                        .setBackgroundColor(ViewUtils.getColor(R.color.colorWhite))
                    itemView.text_view_title.setTextColor(ViewUtils.getColor(R.color.colorTextPureBlack))
                    itemView.text_view_subtitle.makeItVisible()
                    itemView.text_view_subtitle.setTextColor(ViewUtils.getColor(R.color.colorPrimary))
                    itemView.text_view_header1.makeItVisible()
                    itemView.text_view_header1.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
                    itemView.text_view_content1.makeItVisible()
                    itemView.text_view_content1.setTextColor(ViewUtils.getColor(R.color.colorPrimary))
                    itemView.text_view_header2.makeItVisible()
                    itemView.text_view_header2.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
                    itemView.text_view_content2.makeItVisible()
                    itemView.text_view_content2.setTextColor(ViewUtils.getColor(R.color.colorPrimary))

                    itemView.text_view_title.text = item.title
                    itemView.text_view_subtitle.text = item.subtitle
                    itemView.text_view_header1.text = item.header1
                    itemView.text_view_content1.text = item.content1
                    itemView.text_view_header2.text = item.header2
                    itemView.text_view_content2.text = item.content2

                    item.resource?.let {
                        itemView.image_view_resource.loadImage(it)
                    }
                }

                DashboardItem.LAST_YEAR_SECTION -> {
                    itemView.constraint_layout_main_container
                        .setBackgroundColor(ViewUtils.getColor(R.color.colorWhite))
                    itemView.text_view_title.setTextColor(ViewUtils.getColor(R.color.colorTextPureBlack))
                    itemView.text_view_subtitle.makeItVisible()
                    itemView.text_view_subtitle.setTextColor(ViewUtils.getColor(R.color.colorPrimary))
                    itemView.text_view_header1.makeItVisible()
                    itemView.text_view_header1.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
                    itemView.text_view_content1.makeItVisible()
                    itemView.text_view_content1.setTextColor(ViewUtils.getColor(R.color.colorPrimary))
                    itemView.text_view_header2.makeItVisible()
                    itemView.text_view_header2.setTextColor(ViewUtils.getColor(R.color.colorTextRegularSecondary))
                    itemView.text_view_content2.makeItVisible()
                    itemView.text_view_content2.setTextColor(ViewUtils.getColor(R.color.colorPrimary))

                    itemView.text_view_title.text = item.title
                    itemView.text_view_subtitle.text = item.subtitle
                    itemView.text_view_header1.text = item.header1
                    itemView.text_view_content1.text = item.content1
                    itemView.text_view_header2.text = item.header2
                    itemView.text_view_content2.text = item.content2

                    item.resource?.let {
                        itemView.image_view_resource.loadImage(it)
                    }
                }

                DashboardItem.STATUS_SECTION -> {
                    itemView.text_view_title.text = item.title
                    itemView.text_view_header1.text = item.header1
                    itemView.text_view_content1.text = item.content1
                    itemView.text_view_header2.text = item.header2
                    itemView.text_view_content2.text = item.content2

                    if (!TextUtils.isEmpty(item.header3)) {
                        itemView.text_view_header3.makeItVisible()
                        itemView.text_view_header3.text = item.header3
                    } else {
                        itemView.text_view_header3.makeItGone()
                    }

                    if (!TextUtils.isEmpty(item.content3)) {
                        itemView.text_view_content3.makeItVisible()
                        itemView.text_view_content3.text = item.content3
                    } else {
                        itemView.text_view_content3.makeItGone()
                    }

                    item.resource?.let {
                        itemView.image_view_resource.loadImage(it)
                    }
                }
            }
        }

        override fun onClick(view: View) {
            super.onClick(view)

            if (getItem(adapterPosition) != null) {
                mItemClickListener?.onItemClick(view, getItem(adapterPosition)!!)
            }
        }
    }
}