package com.medical.reconscious.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medical.reconscious.R
import com.medical.reconscious.activities.AppointmentScheduleActivity
import com.medical.reconscious.activities.PackagesActivity
import kotlinx.android.synthetic.main.item_fragment_upcoming_appointments.*
import kotlinx.android.synthetic.main.item_fragment_upcoming_appointments.view.*


class UpcomingAppointmentsFragmentItem : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.item_fragment_upcoming_appointments, container, false)

        view.layout_new_session.setOnClickListener(this)

        return view
    }


    override fun onClick(v: View?) {
        if (v == layout_new_session) {
            val intent = Intent(activity, PackagesActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
        }
    }
}