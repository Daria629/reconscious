package com.medical.reconscious.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.medical.reconscious.R
import com.medical.reconscious.adapters.OnPackageItemClickListener
import com.medical.reconscious.adapters.PackageRecyclerAdapter
import com.medical.reconscious.network.APIService
import com.medical.reconscious.network.ServiceBuilder
import com.medical.reconscious.network.respones.PackageResponse
import com.medical.reconscious.prefs
import com.medical.reconscious.utils.APPConstant
import com.medical.reconscious.utils.heightAnimation
import com.medical.reconscious.utils.px
import com.medical.reconscious.utils.showError
import kotlinx.android.synthetic.main.activity_packages.*
import kotlinx.android.synthetic.main.activity_packages.img_back
import kotlinx.android.synthetic.main.activity_packages.layout_next
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PackagesActivity: BaseActivity(), View.OnClickListener, OnPackageItemClickListener {

    val request = ServiceBuilder.buildService(APIService::class.java)

    var packageAry: List<PackageResponse> = listOf()
    var packageIndex = -1

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PackageRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packages)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.itemAnimator = null

        adapter = PackageRecyclerAdapter(packageAry, this)
        recyclerview.adapter = adapter

        img_back.setOnClickListener(this)
        layout_next.setOnClickListener(this)
        layout_skip.setOnClickListener(this)

        heightAnimation(layout_next, 48.px, 0, 0)

        getPackages()
    }

    fun getPackages() {

        showHUD()

        val response = request.getPackages("Bearer " + prefs.token!!)
        response.enqueue(object : Callback<List<PackageResponse>> {

            override fun onResponse(call: Call<List<PackageResponse>>, response: Response<List<PackageResponse>>) {
                hideHUD()
                if (response.body() != null) {

                    packageAry = response.body()!!
                    adapter.setData(packageAry)

                } else if (response.errorBody() != null) {
                    try {
                        showError(this@PackagesActivity, response.errorBody()!!.string())
                    } catch (e: Exception) {
                        Toast.makeText(this@PackagesActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@PackagesActivity, "server error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<PackageResponse>>, t: Throwable) {
                hideHUD()
                Toast.makeText(this@PackagesActivity,"${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View?) {
        if (v == img_back) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        } else if (v == layout_next) {

            val selectPackage = packageAry.get(packageIndex);

            val intent = Intent(this, PaymentStepOneActivity::class.java)
            intent.putExtra(APPConstant.NAME, selectPackage.name)
            intent.putExtra(APPConstant.PRICE, selectPackage.price)
            startActivity(intent)
            overridePendingTransition(R.anim.trans_in, R.anim.trans_out)

        } else if (v == layout_skip) {
            finish()
            overridePendingTransition(R.anim.trans_finish_out, R.anim.trans_finish_in)
        }
    }

    override fun onItemClicked(position: Int, isSelected: Boolean) {

        packageIndex = position

        if (isSelected && layout_next.measuredHeight == 0) heightAnimation(layout_next, 0, 48.px, 200)
        else if (!isSelected && layout_next.measuredHeight != 0) heightAnimation(layout_next, 48.px, 0, 200)

        adapter.setSelection(position)
    }
}