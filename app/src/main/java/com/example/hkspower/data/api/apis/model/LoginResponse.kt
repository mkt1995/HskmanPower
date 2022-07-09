package com.example.hkspower.data.api.apis.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @SerializedName("status") var status: String,
    @SerializedName("message") var messages: String ,
)