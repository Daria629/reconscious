package com.medical.reconscious.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.medical.reconscious.R
import com.medical.reconscious.network.APIService
import com.medical.reconscious.network.ServiceBuilder
import com.medical.reconscious.network.respones.RegisterResponse
import com.medical.reconscious.prefs
import com.medical.reconscious.utils.*
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signin.edit_email
import kotlinx.android.synthetic.main.activity_signin.edit_password
import kotlinx.android.synthetic.main.activity_signin.img_back
import kotlinx.android.synthetic.main.activity_signin.img_error_password
import kotlinx.android.synthetic.main.activity_signin.layout_email
import kotlinx.android.synthetic.main.activity_signin.layout_password
import kotlinx.android.synthetic.main.activity_signup_password.*
import kotlinx.android.synthetic.main.activity_welcome.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity: BaseActivity(), View.OnClickListener {

    val request = ServiceBuilder.buildService(APIService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        layout_signin.setOnClickListener(this)
        layout_forgot_password.setOnClickListener(this)
        img_error_email.setOnClickListener(this)
        img_error_password.setOnClickListener(this)
        img_back.setOnClickListener(this)

        edit_email.setText(getIntent().getStringExtra(APIParams.EMAIL))

        editTextChangeListeners()
    }

    private fun editTextChangeListeners() {
        edit_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_email, img_error_email, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edit_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                setStatusEditBackground(layout_password, img_error_password, true)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    fun login() {

        val jsonObject = JSONObject()

        jsonObject.put(APIParams.EMAIL, edit_email.text.toString())
        jsonObject.put(APIParams.PASSWORD, edit_password.text.toString())

        showHUD()

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = request.login(requestBody)
        response.enqueue(object : Callback<RegisterResponse> {

            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                hideHUD()
                if (response.body() != null) {

                    prefs.token = response.body()!!.key
                    gotoDashboard()

                } else if (response.errorBody() != null) {
                    showError(this@SignInActivity, response.errorBody()!!.string())
                } else {
                    Toast.makeText(this@SignInActivity, "server error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                hideHUD()
                Toast.makeText(this@SignInActivity,"${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun gotoDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onClick(v: View?) {
        if (v == layout_signin) {

            if (!edit_email.text.isValidEmail()) {
                setStatusEditBackground(layout_email, img_error_email, false)
                openSoftKeyboard(this, edit_email)
                return
            } else if (edit_password.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_password, img_error_password, false)
                openSoftKeyboard(this, edit_password)
                return
            } else {
                login()
            }

        } else if (v == layout_forgot_password) {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
        } else if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        } else if (v == img_error_email) {
            setStatusEditBackground(layout_email, img_error_email, true)
        } else if (v == img_error_password) {
            setStatusEditBackground(layout_password, img_error_password, true)
        }
    }
}