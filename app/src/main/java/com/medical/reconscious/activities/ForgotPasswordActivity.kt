package com.medical.reconscious.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.medical.reconscious.R
import com.medical.reconscious.utils.isValidEmail
import com.medical.reconscious.utils.openSoftKeyboard
import com.medical.reconscious.utils.setStatusEditBackground
import kotlinx.android.synthetic.main.activity_forgotpassword.*
import kotlinx.android.synthetic.main.activity_forgotpassword.edit_email
import kotlinx.android.synthetic.main.activity_forgotpassword.img_error
import kotlinx.android.synthetic.main.activity_forgotpassword.layout_email
import kotlinx.android.synthetic.main.activity_welcome.*

class ForgotPasswordActivity: BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)

        layout_enter.setOnClickListener(this)
        img_error.setOnClickListener(this)
        img_back.setOnClickListener(this)

        edit_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_email, img_error, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun onClick(v: View?) {
        if (v == layout_enter) {
            if (edit_email.text.isValidEmail()) {
                val intent = Intent(this, ResetPasswordActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
            } else {
                setStatusEditBackground(layout_email, img_error, false)
                openSoftKeyboard(this, edit_email)
            }
        } else if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        } else if (v == img_error) {
            setStatusEditBackground(layout_email, img_error, true)
        }
    }
}