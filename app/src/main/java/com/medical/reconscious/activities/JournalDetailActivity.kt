package com.medical.reconscious.activities

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.medical.reconscious.R
import com.medical.reconscious.network.APIService
import com.medical.reconscious.network.ServiceBuilder
import com.medical.reconscious.network.respones.JournalResponse
import com.medical.reconscious.prefs
import com.medical.reconscious.utils.*
import com.suke.widget.SwitchButton
import kotlinx.android.synthetic.main.activity_journal_detail.*
import kotlinx.android.synthetic.main.activity_journal_detail.img_back
import kotlinx.android.synthetic.main.activity_signup_contact.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class JournalDetailActivity: BaseActivity(), View.OnClickListener, SwitchButton.OnCheckedChangeListener {

    companion object {
        val TAG = "JournalDetailActivity"
        val REQUEST_CODE = 100
    }

    private val request = ServiceBuilder.buildService(APIService::class.java)
    var feeling = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_detail)

        img_back.setOnClickListener(this)
        layout_open.setOnClickListener(this)
        layout_save.setOnClickListener(this)
        btn_switch.setOnCheckedChangeListener(this)

        val time: String = intent.getStringExtra(APPConstant.CREATED_AT).toString()
        txt_time.text = getTypeFromDate(time, "dd/MM/yyyy - hh:mm a")

        feeling = intent.getStringExtra(APPConstant.FEELING).toString()
        img_feel_mark.setImageResource(getJournalAvater(feeling))
        txt_name.text = getUpercaseString(filterStringWithout_(feeling))

        val isLocked = intent.getBooleanExtra(APPConstant.IS_LOCKED, false)
        if (isLocked) img_lock_mark.setImageResource(R.drawable.icon_lock)
        else img_lock_mark.setImageResource(R.drawable.icon_unlock)
        btn_switch.toggle(isLocked)

        val feeling_title = intent.getStringExtra(APPConstant.TITLE)
        if (feeling_title.isNullOrEmpty() == false) edit_title.setText(feeling_title)

        val feeling_content = intent.getStringExtra(APPConstant.BODY)
        if (feeling_content.isNullOrEmpty() == false) edit_body.setText(feeling_content)
    }

    fun updateJournal() {
        val jsonObject = JSONObject()

        jsonObject.put(
            APIParams.FEELING, filterStringWithoutSpace(feeling).toLowerCase(
                Locale.ROOT
            )
        )
        jsonObject.put(APIParams.IS_LOCKED, btn_switch.isChecked)
        jsonObject.put(APIParams.TITLE, edit_title.text.toString())
        jsonObject.put(APIParams.BODY, edit_body.text.toString())
//        jsonObject.put(APIParams.APPOINTMENT, 0)


        setkProgressHUD()
        showHUD()

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val response = request.updateJournal(
            intent.getIntExtra(APPConstant.ID, 0).toString(),
            requestBody,
            "Bearer " + prefs.token!!
        )
        response.enqueue(object : Callback<JournalResponse> {

            override fun onResponse(
                call: Call<JournalResponse>,
                response: Response<JournalResponse>
            ) {
                hideHUD()
                if (response.body() != null) {
                    finish()
                    overridePendingTransition(R.anim.top_finish_out, R.anim.top_finish_in)
                } else if (response.errorBody() != null) {
                    showError(this@JournalDetailActivity, response.errorBody()!!.string())
                } else {
                    Toast.makeText(this@JournalDetailActivity, "server error", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<JournalResponse>, t: Throwable) {
                hideHUD()
                Toast.makeText(this@JournalDetailActivity, "${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onClick(v: View?) {
        if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        } else if (v == layout_open) {
            val intent = Intent(this, FeelingSelectActivity::class.java)
            intent.putExtra("tag", TAG)
            startActivityForResult(intent, REQUEST_CODE)
            overridePendingTransition(R.anim.top_in, R.anim.top_out)
        } else if (v == layout_save) {
            updateJournal()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            feeling = data!!.getStringExtra("tag").toString()
            img_feel_mark.setImageResource(getJournalAvater(feeling))
            txt_name.text = getUpercaseString(filterStringWithout_(feeling))
        }
    }

    override fun onCheckedChanged(view: SwitchButton?, isChecked: Boolean) {
        if (isChecked) img_lock_mark.setImageResource(R.drawable.icon_lock)
        else img_lock_mark.setImageResource(R.drawable.icon_unlock)
    }
}