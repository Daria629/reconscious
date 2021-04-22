package com.medical.reconscious.utils.decorators

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
import com.medical.reconscious.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

@SuppressLint("UseCompatLoadingForDrawables")
class MySelectorDecorator(context: Activity) : DayViewDecorator {
    private val drawable: Drawable
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable)
    }

    init {
        drawable = context.getDrawable(R.drawable.my_selector)!!
    }
}