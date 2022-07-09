package com.example.hkspower.data.api.apis.repository

import com.example.hkspower.data.api.apis.model.LoginRequest

class AppRepository {
    suspend fun loginUser(body: LoginRequest) = RetrofitInstance.loginApi.getUserData(body)

}