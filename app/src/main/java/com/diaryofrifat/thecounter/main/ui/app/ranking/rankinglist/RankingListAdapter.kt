package com.diaryofrifat.thecounter.main.ui.app.ranking.rankinglist

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.remote.model.Ranker
import com.diaryofrifat.thecounter.main.ui.base.component.BaseAdapter
import com.diaryofrifat.thecounter.main.ui.base.component.BaseViewHolder
import com.diaryofrifat.thecounter.main.ui.base.getString
import kotlinx.android.synthetic.main.item_ranking_list.view.*
import java.util.*

class RankingListAdapter : BaseAdapter<Ranker>() {
    override fun isEqual(left: Ranker, right: Ranker): Boolean {
        return left.id == right.id
    }

    override fun newViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Ranker> {
        return RankerViewHolder(inflate(parent, R.layout.item_ranking_list))
    }

    inner class RankerViewHolder(viewDataBinding: ViewDataBinding) :
        BaseViewHolder<Ranker>(viewDataBinding) {
        override fun bind(item: Ranker) {
            itemView.text_view_id.text =
                String.format(
                    Locale.getDefault(),
                    getString(R.string.ranking_list_id),
                    item.id
                )

            itemView.text_view_name.text = item.name
            itemView.text_view_points.text = item.points
        }
    }
}