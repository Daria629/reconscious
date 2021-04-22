package com.medical.reconscious.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medical.reconscious.R
import kotlinx.android.synthetic.main.item_fragment_sound_experiences.*
import kotlinx.android.synthetic.main.item_fragment_sound_experiences.view.*

class SoundExperiencesFragmentItem: BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.item_fragment_sound_experiences, container, false)
        view.layout_connect.setOnClickListener(this)
        return view
    }


    override fun onClick(v: View?) {
        if (v == layout_connect) {
            Log.d("tag", "start sound")
        }
    }
}