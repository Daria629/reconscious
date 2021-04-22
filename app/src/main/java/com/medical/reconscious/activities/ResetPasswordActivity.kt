package com.medical.reconscious.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.medical.reconscious.R
import com.medical.reconscious.utils.openSoftKeyboard
import com.medical.reconscious.utils.setStatusEditBackground
import kotlinx.android.synthetic.main.activity_resetpassword.*
import kotlinx.android.synthetic.main.activity_welcome.*

class ResetPasswordActivity: BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resetpassword)

        layout_reset_password.setOnClickListener(this)
        img_error_password.setOnClickListener(this)
        img_error_confirmpassword.setOnClickListener(this)
        img_back.setOnClickListener(this)

        edit_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_password, img_error_password, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edit_confirmpassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_confirm_password, img_error_confirmpassword, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun onClick(v: View?) {

        if (v == layout_reset_password) {

            var isContinue = true

            if (edit_password.text.isNullOrEmpty()) {
                isContinue = false
                setStatusEditBackground(layout_password, img_error_password, false)
                openSoftKeyboard(this, edit_password)
                return
            }
            if (edit_confirmpassword.text.isNullOrEmpty()) {
                isContinue = false
                setStatusEditBackground(layout_confirm_password, img_error_confirmpassword, false)
                openSoftKeyboard(this, edit_confirmpassword)
                return
            }
            if (!edit_password.text.equals(edit_confirmpassword.text)) {
                isContinue = false
                setStatusEditBackground(layout_confirm_password, img_error_confirmpassword, false)
                openSoftKeyboard(this, edit_confirmpassword)
                return
            }

            if (isContinue) {

            }

        } else if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        } else if (v == img_error_password) {
            setStatusEditBackground(layout_password, img_error_password, true)
        } else if (v == img_error_confirmpassword) {
            setStatusEditBackground(layout_confirm_password, img_error_confirmpassword, true)
        }
    }
}