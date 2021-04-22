package com.medical.reconscious.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import com.medical.reconscious.R
import com.medical.reconscious.utils.APPConstant
import com.tenbis.library.consts.CardType
import com.tenbis.library.listeners.OnCreditCardStateChanged
import com.tenbis.library.models.CreditCard
import kotlinx.android.synthetic.main.activity_payment_step_one.*

class PaymentStepOneActivity: BaseActivity(), View.OnClickListener, OnCreditCardStateChanged {

    var price = 0
    var name = ""
    var isValidCard = false
    lateinit var card: CreditCard

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_step_one)

        google_pay.setOnClickListener(this)
        layout_next.setOnClickListener(this)
        layout_skip.setOnClickListener(this)
        img_back.setOnClickListener(this)

        edit_credit_card.attachLifecycle(lifecycle)
        edit_credit_card.addOnCreditCardStateChangedListener(this)

        price = intent.getFloatExtra(APPConstant.PRICE, 0F).toInt()
        name = intent.getStringExtra(APPConstant.NAME).toString()

        txt_price.text = "$$price"

        val title = "$" + price.toString() + " " + getString(R.string.payfor) + " " + name

        val spannable = SpannableString(title)
        spannable.setSpan(ForegroundColorSpan(getColor(R.color.colorMain)), 0, ("$" + price.toString()).length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(getColor(R.color.colorMain)), ("$" + price.toString() + " " + getString(R.string.payfor) + " ").length, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        txt_title2.setText(spannable)
    }

    override fun onCreditCardCvvValid(cvv: String) {

    }

    override fun onCreditCardExpirationDateValid(month: Int, year: Int) {

    }

    override fun onCreditCardNumberValid(creditCardNumber: String) {

    }

    override fun onCreditCardTypeFound(cardType: CardType) {

    }

    override fun onCreditCardValid(creditCard: CreditCard) {
        isValidCard = true
        card = creditCard
    }

    override fun onInvalidCardTyped() {
        isValidCard = false
    }

    override fun onClick(v: View?) {
        if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        } else if (v == layout_next) {

//            if (isValidCard) {
                val intent = Intent(this, PaymentStepTwoActivity::class.java)
                intent.putExtra(APPConstant.CARDNUMBER, card.cardNumber)
                intent.putExtra(APPConstant.CVV, card.cvv)
                intent.putExtra(APPConstant.CARDTYPE, card.cardType.toString())
                intent.putExtra(APPConstant.EXPIREDMONTH, card.expiryMonth)
                intent.putExtra(APPConstant.EXPIREDYEAR, card.expiryYear)
            intent.putExtra(APPConstant.NAME, name)
            intent.putExtra(APPConstant.PRICE, price)
                startActivity(intent)
                overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
//            }

        } else if (v == layout_skip) {
//            val intent = Intent(this, PaymentStepTwoActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
        }
    }
}