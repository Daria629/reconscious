package com.medical.reconscious.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.medical.reconscious.R
import com.medical.reconscious.utils.APIParams
import com.medical.reconscious.utils.APPConstant
import com.medical.reconscious.utils.getCardType
import com.medical.reconscious.utils.getLastFourNumberInCard
import kotlinx.android.synthetic.main.activity_payment_step_confirm.*

class PaymentStepConfirmActivity: BaseActivity(), View.OnClickListener {

    var name = ""
    var price = 0
    var cardNumber = ""
    var cardMonth = 0
    var cardYear = 0
    var cardType = ""
    var cardCVV = ""
    var username = ""
    var street = ""
    var city = ""
    var state = ""
    var zipCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_step_confirm)

        img_back.setOnClickListener(this)
        layout_next.setOnClickListener(this)
        layout_skip.setOnClickListener(this)

        price = intent.getIntExtra(APPConstant.PRICE, 0)
        name = intent.getStringExtra(APPConstant.NAME).toString()
        cardNumber = intent.getStringExtra(APPConstant.CARDNUMBER).toString()
        cardMonth = intent.getIntExtra(APPConstant.EXPIREDMONTH, 0)
        cardYear = intent.getIntExtra(APPConstant.EXPIREDYEAR, 0)
        cardType = intent.getStringExtra(APPConstant.CARDTYPE).toString()
        cardCVV = intent.getStringExtra(APPConstant.CVV).toString()
        username = intent.getStringExtra(APIParams.USERNAME).toString()
        street = intent.getStringExtra(APIParams.STREET).toString()
        city = intent.getStringExtra(APIParams.CITY).toString()
        state = intent.getStringExtra(APIParams.STATE).toString()
        zipCode = intent.getStringExtra(APIParams.ZIPCODE).toString()

        initView()

    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        txt_name.text = username
        txt_address.text = street
        txt_zipcode.text = "$city, $state $zipCode"
        txt_number.text = "XXXX-XXXX-XXXX-" + getLastFourNumberInCard(cardNumber)
        txt_expired.text = cardMonth.toString() + "/" + cardYear
        txt_package.text = name
        txt_price.text = "$" + price.toString()
        txt_price1.text = "$" + price.toString()

        img_card.setImageResource(getCardType(cardType))
    }

    override fun onClick(v: View?) {
        if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        } else if (v == layout_next) {
            val intent = Intent(this, PaymentStepConfirmedActivity::class.java)
            intent.putExtra(APPConstant.CARDNUMBER, cardNumber)
            intent.putExtra(APPConstant.NAME, name)
            intent.putExtra(APPConstant.PRICE, price)
            startActivity(intent)
            overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
        }
    }
}