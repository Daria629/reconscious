package com.medical.reconscious.adapters

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.medical.reconscious.R
import com.medical.reconscious.network.respones.JournalResponse
import com.medical.reconscious.utils.getTypeFromDate
import com.medical.reconscious.utils.inflate
import com.medical.reconscious.utils.timeFormart
import kotlinx.android.synthetic.main.item_recyclerview_journal.view.*

class JournalRecyclerAdapter (private var journalList: List<JournalResponse>, val itemClickListener: OnJournalItemClickListener) : RecyclerView.Adapter<JournalRecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val inflatedView = parent.inflate(R.layout.item_recyclerview_journal, false)
        return Holder(inflatedView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = journalList.get(position)
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int {
        return journalList.size
    }

    fun setData(data: List<JournalResponse>) {
        journalList = data
        notifyDataSetChanged()
    }

    class Holder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v

        init {

        }

        fun bind(journal: JournalResponse, clickListener: OnJournalItemClickListener) {

            if (journal.title.isNullOrEmpty()) {
                view.txt_name.text = journal.created_at?.let { getTypeFromDate(it, "MMMM dd, yyyy") }
                view.txt_time.text = journal.created_at?.let { getTypeFromDate(it, "hh:mm a") }
            } else {
                view.txt_name.text = journal.title
                view.txt_time.text = journal.created_at?.let { getTypeFromDate(it, "dd/MM/yyyy hh:mm a") }
            }

            view.layout_main.setOnClickListener {
                clickListener.onItemClicked(adapterPosition)
            }
        }
    }
}

interface OnJournalItemClickListener{
    fun onItemClicked(position: Int)
}