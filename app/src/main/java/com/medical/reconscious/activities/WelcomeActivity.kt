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
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.activity_welcome.edit_email
import kotlinx.android.synthetic.main.activity_welcome.layout_email

class WelcomeActivity: BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        layout_continue.setOnClickListener(this)
        layout_create_button.setOnClickListener(this)
        img_error.setOnClickListener(this)

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

        if (v == layout_continue) {

            if (edit_email.text.isValidEmail()) {
                val intent = Intent(this, SignInActivity::class.java)
                intent.putExtra(APIParams.EMAIL, edit_email.text.toString())
                startActivity(intent)
                overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
            } else {
                setStatusEditBackground(layout_email, img_error, false)
                openSoftKeyboard(this, edit_email)
            }

        } else if (v == layout_create_button) {

            if (edit_email.text.isValidEmail()) {
                val intent = Intent(this, SignUpContactActivity::class.java)
                intent.putExtra(APIParams.EMAIL, edit_email.text.toString())
                startActivity(intent)
                overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
            } else {
                setStatusEditBackground(layout_email, img_error, false)
                openSoftKeyboard(this, edit_email)
            }

        } else if (v == img_error) {
            edit_email.setText("")
            setStatusEditBackground(layout_email, img_error, true)
        }
    }
}