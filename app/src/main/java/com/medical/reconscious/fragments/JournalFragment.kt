package com.medical.reconscious.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.medical.reconscious.R
import com.medical.reconscious.activities.FeelingSelectActivity
import com.medical.reconscious.activities.JournalDetailActivity
import com.medical.reconscious.adapters.JournalRecyclerAdapter
import com.medical.reconscious.adapters.OnJournalItemClickListener
import com.medical.reconscious.network.APIService
import com.medical.reconscious.network.ServiceBuilder
import com.medical.reconscious.network.respones.JournalResponse
import com.medical.reconscious.prefs
import com.medical.reconscious.utils.APIParams
import com.medical.reconscious.utils.APPConstant
import com.medical.reconscious.utils.showError
import kotlinx.android.synthetic.main.fragment_journal.*
import kotlinx.android.synthetic.main.fragment_journal.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JournalFragment: BaseFragment(), View.OnClickListener, OnJournalItemClickListener {

    companion object {
        val TAG = "JournalFragment"
    }

    val request = ServiceBuilder.buildService(APIService::class.java)
    var journalAry: List<JournalResponse> = listOf()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: JournalRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_journal, container, false)

        view.img_add.setOnClickListener(this)

        linearLayoutManager = LinearLayoutManager(activity)
        view.recyclerview.layoutManager = linearLayoutManager

        adapter = JournalRecyclerAdapter(journalAry, this)
        view.recyclerview.adapter = adapter

        return view
    }

    override fun onStart() {
        super.onStart()
        getJournals()
    }

    fun getJournals() {

        showHUD()

        val response = request.getJournals("Bearer " + prefs.token!!)
        response.enqueue(object : Callback<List<JournalResponse>> {

            override fun onResponse(call: Call<List<JournalResponse>>, response: Response<List<JournalResponse>>) {
                hideHUD()
                if (response.body() != null) {

                    journalAry = response.body()!!
                    adapter.setData(journalAry)

                } else if (response.errorBody() != null) {
                    activity?.let { showError(it, response.errorBody()!!.string()) }
                } else {
                    Toast.makeText(activity, "server error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<JournalResponse>>, t: Throwable) {
                hideHUD()
                Toast.makeText(activity,"${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View?) {
        if (v == img_add) {
            val intent = Intent(activity, FeelingSelectActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.top_in, R.anim.top_out)
        }
    }

    override fun onItemClicked(position: Int) {

        val journal = journalAry.get(position)

        val intent = Intent(activity, JournalDetailActivity::class.java)
        intent.putExtra(APPConstant.ID, journal.id)
        intent.putExtra(APPConstant.CREATED_AT, journal.created_at)
        intent.putExtra(APPConstant.FEELING, journal.feeling)
        intent.putExtra(APPConstant.BODY, journal.body)
        intent.putExtra(APPConstant.TITLE, journal.title)
        intent.putExtra(APPConstant.APPOINTMENT, journal.appointment)
        intent.putExtra(APPConstant.IS_LOCKED, journal.is_locked)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.trans_in, R.anim.trans_out)
    }
}