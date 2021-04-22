package com.medical.reconscious.network

import com.medical.reconscious.network.respones.JournalResponse
import com.medical.reconscious.network.respones.PackageResponse
import com.medical.reconscious.network.respones.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @POST("auth/registration/")
    fun register(@Body requestBody: RequestBody): Call<RegisterResponse>

    @POST("auth/login/")
    fun login(@Body requestBody: RequestBody): Call<RegisterResponse>

    @GET("appointments/packages/")
    fun getPackages(@Header("Authorization") token: String) : Call<List<PackageResponse>>

    @POST("journal/entries/")
    fun createJournal(@Body requestBody: RequestBody, @Header("Authorization") token: String) : Call<JournalResponse>

    @GET("journal/entries/")
    fun getJournals(@Header("Authorization") token: String) : Call<List<JournalResponse>>

    @PATCH("journal/entries/{id}/")
    fun updateJournal(@Path("id") id: String, @Body requestBody: RequestBody, @Header("Authorization") token: String) : Call<JournalResponse>
}