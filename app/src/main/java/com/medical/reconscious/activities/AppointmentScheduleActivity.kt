package com.medical.reconscious.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.medical.reconscious.R
import com.medical.reconscious.utils.APPConstant
import com.medical.reconscious.utils.decorators.HighlightWeekendsDecorator
import com.medical.reconscious.utils.decorators.MySelectorDecorator
import com.medical.reconscious.utils.decorators.OneDayDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_appointment_schedule.*

class AppointmentScheduleActivity: BaseActivity(), View.OnClickListener, OnDateSelectedListener {

    private val oneDayDecorator: OneDayDecorator = OneDayDecorator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_schedule)

        layout_next.setOnClickListener(this)

        txt_title2.text = intent.getStringExtra(APPConstant.NAME).toString()

        calendar_view.setOnDateChangedListener(this)
        calendar_view.showOtherDates = MaterialCalendarView.SHOW_ALL
        calendar_view.addDecorators(
            MySelectorDecorator(this),
            HighlightWeekendsDecorator(),
            oneDayDecorator
        )
    }

    override fun onClick(v: View?) {
        if (v == layout_next) {
            val mainIntent = Intent(this, DashboardActivity::class.java)
            startActivity(mainIntent)
            finishAffinity()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        oneDayDecorator.setDate(date.date)
        calendar_view.invalidateDecorators()
    }
}