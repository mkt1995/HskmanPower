package com.example.hkspower.data.api.apis


import androidx.annotation.Keep
import com.example.hkspower.data.api.apis.model.LoginRequest
import com.example.hkspower.data.api.apis.model.LoginResponse
import okhttp3.Call

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET




interface API {

    @POST("login.php")
    suspend fun getUserData(@Body body: LoginRequest): Response<LoginResponse>

}