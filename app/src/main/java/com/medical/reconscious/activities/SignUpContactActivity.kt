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
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signup_contact.*
import kotlinx.android.synthetic.main.activity_signup_contact.edit_email
import kotlinx.android.synthetic.main.activity_signup_contact.img_back
import kotlinx.android.synthetic.main.activity_signup_contact.img_error_email
import kotlinx.android.synthetic.main.activity_signup_contact.layout_email
import kotlinx.android.synthetic.main.activity_welcome.*

class SignUpContactActivity: BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signup_contact)

        img_back.setOnClickListener(this)
        img_error_firstname.setOnClickListener(this)
        img_error_lastname.setOnClickListener(this)
        img_error_email.setOnClickListener(this)
        img_error_phone.setOnClickListener(this)
        layout_next.setOnClickListener(this)

        edit_email.setText(getIntent().getStringExtra(APIParams.EMAIL))

        editTextChangeListeners()
    }

    private fun editTextChangeListeners() {

        edit_firstname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_firstname, img_error_firstname, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edit_lastname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_lastname, img_error_lastname, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edit_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_email, img_error_email, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edit_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_phone, img_error_phone, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun onClick(v: View?) {

        if (v == layout_next) {

            if (edit_firstname.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_firstname, img_error_firstname, false)
                openSoftKeyboard(this, edit_firstname)
                return
            } else if (edit_lastname.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_lastname, img_error_lastname, false)
                openSoftKeyboard(this, edit_lastname)
                return
            } else if (!edit_email.text.isValidEmail()) {
                setStatusEditBackground(layout_email, img_error_email, false)
                openSoftKeyboard(this, edit_email)
                return
            } else if (edit_phone.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_phone, img_error_phone, false)
                openSoftKeyboard(this, edit_phone)
                return
            } else {
                val intent = Intent(this, SignUpMoreDetailActivity::class.java)
                intent.putExtra(APIParams.EMAIL, edit_email.text.toString())
                intent.putExtra(APIParams.FIRST_NAME, edit_firstname.text.toString())
                intent.putExtra(APIParams.LAST_NAME, edit_firstname.text.toString())
                intent.putExtra(APIParams.PHONE, edit_phone.text.toString())
                startActivity(intent)
                overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
            }

        } else if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        } else if (v == img_error_firstname) {
            setStatusEditBackground(layout_firstname, img_error_firstname, true)
        } else if (v == img_error_lastname) {
            setStatusEditBackground(layout_lastname, img_error_lastname, true)
        } else if (v == img_error_email) {
            setStatusEditBackground(layout_email, img_error_email, true)
        } else if (v == img_error_phone) {
            setStatusEditBackground(layout_phone, img_error_phone, true)
        }
    }
}