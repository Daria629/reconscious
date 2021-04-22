package com.medical.reconscious.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.medical.reconscious.R
import com.medical.reconscious.utils.APIParams
import com.medical.reconscious.utils.isValidEmail
import com.medical.reconscious.utils.openSoftKeyboard
import com.medical.reconscious.utils.setStatusEditBackground
import kotlinx.android.synthetic.main.activity_resetpassword.*
import kotlinx.android.synthetic.main.activity_signup_contact.*
import kotlinx.android.synthetic.main.activity_signup_moredetail.*
import kotlinx.android.synthetic.main.activity_signup_moredetail.img_back
import kotlinx.android.synthetic.main.activity_signup_moredetail.layout_next
import java.util.*

class SignUpMoreDetailActivity: BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signup_moredetail)

        layout_next.setOnClickListener(this)
        img_back.setOnClickListener(this)

        edit_birthday.addTextChangedListener(object : TextWatcher {

            var edited = false
            val dividerCharacter = "-"

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (edited) {
                    edited = false
                    return
                }

                var working = getEditText()

                working = manageDateDivider(working, 4, start, before)
                working = manageDateDivider(working, 7, start, before)

                edited = true
                edit_birthday.setText(working)
                edit_birthday.setSelection(edit_birthday.text.length)
            }
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_birthday, img_error_birthday, true)
            }

            private fun manageDateDivider(working: String, position : Int, start: Int, before: Int) : String{
                if (working.length == position) {
                    return if (before <= position && start < position)
                        working + dividerCharacter
                    else
                        working.dropLast(1)
                }
                return working
            }

            private fun getEditText() : String {
                return if (edit_birthday.text.length >= 10)
                    edit_birthday.text.toString().substring(0,10)
                else
                    edit_birthday.text.toString()
            }
        })
    }

    override fun onClick(v: View?) {

        if (v == layout_next) {

            if (edit_country.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_country, img_error_country, false)
                openSoftKeyboard(this, edit_country)
                return
            } else if (edit_state.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_state, img_error_statu, false)
                openSoftKeyboard(this, edit_state)
                return
            } else if (edit_birthday.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_birthday, img_error_birthday, false)
                openSoftKeyboard(this, edit_birthday)
                return
            } else {
                val intent = Intent(this, SignUpPasswordActivity::class.java)
                intent.putExtra(APIParams.EMAIL, getIntent().getStringExtra(APIParams.EMAIL))
                intent.putExtra(APIParams.FIRST_NAME, getIntent().getStringExtra(APIParams.FIRST_NAME))
                intent.putExtra(APIParams.LAST_NAME, getIntent().getStringExtra(APIParams.LAST_NAME))
                intent.putExtra(APIParams.PHONE, getIntent().getStringExtra(APIParams.PHONE))
                intent.putExtra(APIParams.BIRTHDAY, edit_birthday.text.toString())
                intent.putExtra(APIParams.COUNTRY, edit_country.text.toString())
                intent.putExtra(APIParams.STATE, edit_state.text.toString())
                startActivity(intent)
                overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
            }

        } else if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        }
    }
}