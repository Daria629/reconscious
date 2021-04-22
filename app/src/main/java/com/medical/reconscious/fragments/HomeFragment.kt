package com.medical.reconscious.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.medical.reconscious.R
import com.medical.reconscious.activities.BaseActivity
import com.medical.reconscious.network.APIService
import com.medical.reconscious.network.ServiceBuilder
import com.medical.reconscious.network.respones.PackageResponse
import com.medical.reconscious.network.respones.RegisterResponse
import com.medical.reconscious.prefs
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment: BaseFragment() {

    val request = ServiceBuilder.buildService(APIService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

}