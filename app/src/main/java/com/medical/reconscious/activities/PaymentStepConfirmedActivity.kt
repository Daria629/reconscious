package com.medical.reconscious.activities

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import com.medical.reconscious.R
import com.medical.reconscious.utils.APPConstant
import com.medical.reconscious.utils.getLastFourNumberInCard
import kotlinx.android.synthetic.main.activity_payment_step_confirmed.*

class PaymentStepConfirmedActivity: BaseActivity(), View.OnClickListener {

    var packagename = ""
    var packagePrice = 0
    var cardNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_step_confirmed)

        img_back.setOnClickListener(this)
        layout_next.setOnClickListener(this)

        packagename = intent.getStringExtra(APPConstant.NAME).toString()
        packagePrice = intent.getIntExtra(APPConstant.PRICE, 0)
        cardNumber = intent.getStringExtra(APPConstant.CARDNUMBER).toString()

        val detail1 = "You will be billed " + packagePrice.toString() + " for " + packagename + " to Card Ending In " + getLastFourNumberInCard(cardNumber) + "."

        val spannable = SpannableString(detail1)
        spannable.setSpan(ForegroundColorSpan(getColor(R.color.colorMain)), ("You will be billed").length, ("You will be billed " + packagePrice.toString()).length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(getColor(R.color.colorMain)), ("You will be billed " + packagePrice.toString() + " for ").length, ("You will be billed " + packagePrice.toString() + " for " + packagename).length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(getColor(R.color.colorMain)), ("You will be billed " + packagePrice.toString() + " for " + packagename + " to Card Ending In ").length, detail1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        txt_detail1.setText(spannable)

    }

    override fun onClick(v: View?) {
        if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        } else if (v == layout_next) {
            val intent = Intent(this, AppointmentScheduleActivity::class.java)
            intent.putExtra(APPConstant.NAME, packagename)
            startActivity(intent)
            overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
        }
    }
}