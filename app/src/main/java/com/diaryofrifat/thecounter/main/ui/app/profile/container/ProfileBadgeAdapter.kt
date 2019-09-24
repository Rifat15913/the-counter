package com.diaryofrifat.thecounter.main.ui.app.profile.container

import android.graphics.drawable.PictureDrawable
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.remote.model.ProfileBadge
import com.diaryofrifat.thecounter.main.ui.base.component.BaseAdapter
import com.diaryofrifat.thecounter.main.ui.base.component.BaseViewHolder
import com.diaryofrifat.thecounter.utils.libs.glidecustom.GlideApp
import com.diaryofrifat.thecounter.utils.libs.glidecustom.SvgSoftwareLayerSetter
import kotlinx.android.synthetic.main.item_profile_badge.view.*


class ProfileBadgeAdapter : BaseAdapter<ProfileBadge>() {

    override fun isEqual(left: ProfileBadge, right: ProfileBadge): Boolean {
        return left.title == right.title
    }

    override fun newViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ProfileBadge> {
        return ProfileBadgeViewHolder(inflate(parent, R.layout.item_profile_badge))
    }

    inner class ProfileBadgeViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<ProfileBadge>(viewDataBinding) {

        override fun bind(item: ProfileBadge) {
            GlideApp.with(itemView)
                .`as`(PictureDrawable::class.java)
                .load(item.imageSource)
                .apply(
                    RequestOptions()
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .centerCrop()
                )
                .transition(withCrossFade())
                .listener(SvgSoftwareLayerSetter())
                .into(itemView.image_view_badge)

            setClickListener(itemView.image_view_badge)
        }

        override fun onClick(view: View) {
            super.onClick(view)

            getItem(adapterPosition)?.let {
                mItemClickListener?.onItemClick(view, it)
            }
        }
    }
}