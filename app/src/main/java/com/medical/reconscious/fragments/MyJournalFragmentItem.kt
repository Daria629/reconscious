package com.medical.reconscious.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medical.reconscious.R
import kotlinx.android.synthetic.main.item_fragment_my_journal.*
import kotlinx.android.synthetic.main.item_fragment_my_journal.view.*

class MyJournalFragmentItem : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.item_fragment_my_journal, container, false)
        view.layout_open_journal.setOnClickListener(this)
        return view
    }


    override fun onClick(v: View?) {
        if (v == layout_open_journal) {
            Log.d("tag", "start new session")
        }
    }
}