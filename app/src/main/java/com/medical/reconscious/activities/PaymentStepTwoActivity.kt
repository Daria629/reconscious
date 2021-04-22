package com.medical.reconscious.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.medical.reconscious.R
import com.medical.reconscious.utils.APIParams
import com.medical.reconscious.utils.APPConstant
import com.medical.reconscious.utils.openSoftKeyboard
import com.medical.reconscious.utils.setStatusEditBackground
import kotlinx.android.synthetic.main.activity_payment_step_two.*

class PaymentStepTwoActivity: BaseActivity(), View.OnClickListener {

    var name = ""
    var price = 0
    var cardNumber = ""
    var cardMonth = 0
    var cardYear = 0
    var cardType = ""
    var cardCVV = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_payment_step_two)
        img_back.setOnClickListener(this)
        layout_next.setOnClickListener(this)
        layout_skip.setOnClickListener(this)

        editTextChangeListeners()

        price = intent.getIntExtra(APPConstant.PRICE, 0)
        name = intent.getStringExtra(APPConstant.NAME).toString()
        cardNumber = intent.getStringExtra(APPConstant.CARDNUMBER).toString()
        cardMonth = intent.getIntExtra(APPConstant.EXPIREDMONTH, 0)
        cardYear = intent.getIntExtra(APPConstant.EXPIREDYEAR, 0)
        cardType = intent.getStringExtra(APPConstant.CARDTYPE).toString()
        cardCVV = intent.getStringExtra(APPConstant.CVV).toString()

    }

    private fun editTextChangeListeners() {

        edit_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_name, img_error_name, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edit_street.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_street, img_error_street, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edit_city.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_city, img_error_city, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edit_state.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_state, img_error_state, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edit_zipcode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_zipcode, img_error_zipcode, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun onClick(v: View?) {
        if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        } else if (v == layout_next) {

            if (edit_name.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_name, img_error_name, false)
                openSoftKeyboard(this, edit_name)
                return
            } else if (edit_street.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_street, img_error_street, false)
                openSoftKeyboard(this, edit_street)
                return
            } else if (edit_city.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_city, img_error_city, false)
                openSoftKeyboard(this, edit_city)
                return
            } else if (edit_state.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_state, img_error_state, false)
                openSoftKeyboard(this, edit_state)
                return
            } else if (edit_zipcode.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_zipcode, img_error_zipcode, false)
                openSoftKeyboard(this, edit_zipcode)
                return
            }

            val intent = Intent(this, PaymentStepConfirmActivity::class.java)
            intent.putExtra(APPConstant.CARDNUMBER, cardNumber)
            intent.putExtra(APPConstant.CVV, cardCVV)
            intent.putExtra(APPConstant.CARDTYPE, cardType)
            intent.putExtra(APPConstant.EXPIREDMONTH, cardMonth)
            intent.putExtra(APPConstant.EXPIREDYEAR, cardYear)
            intent.putExtra(APPConstant.NAME, name)
            intent.putExtra(APPConstant.PRICE, price)
            intent.putExtra(APIParams.USERNAME, edit_name.text.toString())
            intent.putExtra(APIParams.STREET, edit_street.text.toString())
            intent.putExtra(APIParams.CITY, edit_city.text.toString())
            intent.putExtra(APIParams.STATE, edit_state.text.toString())
            intent.putExtra(APIParams.ZIPCODE, edit_zipcode.text.toString())
            startActivity(intent)
            overridePendingTransition(R.anim.trans_in, R.anim.trans_out)

        } else if (v == layout_skip) {
//            val intent = Intent(this, PaymentStepConfirmActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
        }
    }
}