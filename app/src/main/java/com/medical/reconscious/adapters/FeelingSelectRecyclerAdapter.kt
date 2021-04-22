package com.medical.reconscious.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.medical.reconscious.R
import com.medical.reconscious.utils.heightAnimation
import com.medical.reconscious.utils.inflate
import kotlinx.android.synthetic.main.item_recyclerview_feelings.view.*

class FeelingSelectRecyclerAdapter (private var feelList: List<String>, private var feelIconList: List<Int>, val itemClickListener: OnFeelingItemClickListener) : RecyclerView.Adapter<FeelingSelectRecyclerAdapter.Holder>() {

    var selected = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val inflatedView = parent.inflate(R.layout.item_recyclerview_feelings, false)
        return Holder(inflatedView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val feelItem = feelList.get(position)
        val feelIcon = feelIconList.get(position)
        holder.bind(feelItem, feelIcon, position, selected, itemClickListener)
    }

    fun setSelection(index: Int) {
        if(selected == index) {
            selected = -1
            notifyItemChanged(index)
        }
        else if(selected != -1) {
            val old = selected
            selected = index
            notifyItemChanged(index)
            notifyItemChanged(old)
        }
        else {
            selected = index
            notifyItemChanged(index)
        }

    }

    override fun getItemCount(): Int {
        return feelList.size
    }

    class Holder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v
        private var isSelected = false

        init {

        }

        fun bind(feel: String, feelIcon: Int, position: Int, selected: Int, clickListener: OnFeelingItemClickListener) {

            view.txt_name.text = feel
            view.img_mark.setImageResource(feelIcon)

            if(position == selected) {
                isSelected = false
                selectCell()
            }
            else {
                isSelected = true
                unSelectCell()
            }

            view.layout_main.setOnClickListener {
                clickListener.onItemClicked(position, isSelected)
            }
        }

        private fun selectCell() {

            view.layout_main.setBackgroundResource(R.drawable.layout_fragment_item_selected)
            view.txt_name.setTextColor(view.context.getColor(R.color.white))
            view.img_mark.setColorFilter(view.context.getColor(R.color.white))
        }

        private fun unSelectCell() {
            view.layout_main.setBackgroundResource(R.drawable.layout_fragment_item)
            view.txt_name.setTextColor(view.context.getColor(R.color.black))
            view.img_mark.setColorFilter(view.context.getColor(R.color.black))
        }
    }
}

interface OnFeelingItemClickListener{
    fun onItemClicked(position: Int, isSelected: Boolean)
}