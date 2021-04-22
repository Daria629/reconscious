package com.medical.reconscious.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.medical.reconscious.R
import com.medical.reconscious.network.APIService
import com.medical.reconscious.network.ServiceBuilder
import com.medical.reconscious.network.respones.RegisterResponse
import com.medical.reconscious.prefs
import com.medical.reconscious.utils.APIParams
import com.medical.reconscious.utils.openSoftKeyboard
import com.medical.reconscious.utils.setStatusEditBackground
import com.medical.reconscious.utils.showError
import kotlinx.android.synthetic.main.activity_signup_contact.*
import kotlinx.android.synthetic.main.activity_signup_password.*
import kotlinx.android.synthetic.main.activity_signup_password.img_back
import kotlinx.android.synthetic.main.activity_signup_password.layout_next
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpPasswordActivity: BaseActivity(), View.OnClickListener {

    val request = ServiceBuilder.buildService(APIService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_password)
        layout_next.setOnClickListener(this)
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

    fun register() {

        val jsonObject = JSONObject()

        jsonObject.put(APIParams.EMAIL, getIntent().getStringExtra(APIParams.EMAIL))
        jsonObject.put(APIParams.PASSWORD1, edit_password.text.toString())
        jsonObject.put(APIParams.PASSWORD2, edit_confirmpassword.text.toString())
        jsonObject.put(APIParams.FIRST_NAME, getIntent().getStringExtra(APIParams.FIRST_NAME))
        jsonObject.put(APIParams.LAST_NAME, getIntent().getStringExtra(APIParams.LAST_NAME))
        jsonObject.put(APIParams.COUNTRY, getIntent().getStringExtra(APIParams.COUNTRY))
        jsonObject.put(APIParams.STATE, getIntent().getStringExtra(APIParams.STATE))
        jsonObject.put(APIParams.GENDER, "female")
        jsonObject.put(APIParams.BIRTHDAY, getIntent().getStringExtra(APIParams.BIRTHDAY))

        showHUD()

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = request.register(requestBody)
        response.enqueue(object : Callback<RegisterResponse> {

            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                hideHUD()
                if (response.body() != null) {

                    prefs.token = response.body()!!.key
                    gotoDashboard()

                } else if (response.errorBody() != null) {
                    showError(this@SignUpPasswordActivity, response.errorBody()!!.string())
                } else {
                    Toast.makeText(this@SignUpPasswordActivity, "server error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                hideHUD()
                Toast.makeText(this@SignUpPasswordActivity,"${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun gotoDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onClick(v: View?) {
        if (v == layout_next) {

            if (edit_password.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_password, img_error_password, false)
                openSoftKeyboard(this, edit_password)
                return
            } else if (edit_confirmpassword.text.isNullOrEmpty()) {
                setStatusEditBackground(layout_confirm_password, img_error_confirmpassword, false)
                openSoftKeyboard(this, edit_confirmpassword)
                return
            } else if (!edit_password.text.toString().equals(edit_confirmpassword.text.toString())) {
                setStatusEditBackground(layout_confirm_password, img_error_confirmpassword, false)
                openSoftKeyboard(this, edit_confirmpassword)
                return
            } else {
                register()
            }

        } else if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        }
    }
}