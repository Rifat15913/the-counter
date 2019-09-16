package com.itechsoftsolutions.mtcore.main.ui.app.landing.container

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.local.model.NavigationDrawerItem
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseAdapter
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseViewHolder
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.libs.GlideUtils

class DrawerAdapter : BaseAdapter<NavigationDrawerItem>() {
    override fun isEqual(left: NavigationDrawerItem, right: NavigationDrawerItem): Boolean {
        return left.title.toLowerCase() == right.title.toLowerCase()
    }

    override fun newViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NavigationDrawerItem> {
        return DrawerItemViewHolder(inflate(parent, R.layout.item_navigation_drawer_menu))
    }

    inner class DrawerItemViewHolder(binding: ViewDataBinding) : BaseViewHolder<NavigationDrawerItem>(binding) {

        private val mBinding = binding as com.itechsoftsolutions.mtcore.databinding.ItemNavigationDrawerMenuBinding

        override fun bind(item: NavigationDrawerItem) {
            mBinding.textViewTitle.text = DataUtils.toTitleCase(item.title)

            item.resourceId?.let {
                GlideUtils.normal(mBinding.imageViewIcon, it)
            }

            setClickListener(itemView)
        }

        override fun onClick(view: View) {
            if (getItem(adapterPosition) != null) {
                mItemClickListener?.onItemClick(view, getItem(adapterPosition)!!)
            }
        }
    }
}