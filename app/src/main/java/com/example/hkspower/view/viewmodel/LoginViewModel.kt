package com.example.hkspower.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hkspower.R
import com.example.hkspower.app.MyApplication
import com.example.hkspower.data.api.apis.model.LoginRequest
import com.example.hkspower.data.api.apis.model.LoginResponse
import com.example.hkspower.data.api.apis.repository.AppRepository
import com.example.hkspower.utils.Event
import com.example.hkspower.utils.Resource
import com.example.hkspower.utils.Utils
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class LoginViewModel
    (
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app)
{

    private val _loginDetails = MutableLiveData<Event<Resource<LoginResponse>>>()
    val loginDetails: LiveData<Event<Resource<LoginResponse>>> = _loginDetails
    fun logDet(body: LoginRequest) = viewModelScope.launch {
        callOtp(body)
    }
    private suspend fun callOtp(body: LoginRequest) {
        _loginDetails.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.loginUser(body)
                _loginDetails.postValue(handleOtpResponse(response))
            } else {
                _loginDetails.postValue(
                    Event(
                        Resource.Error(getApplication<MyApplication>().getString(
                            R.string.no_internet_connection)))
                )
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _loginDetails.postValue(
                        Event(
                            Resource.Error(
                                t.message.toString()
                            ))
                    )
                }
                else -> {
                    _loginDetails.postValue(
                        Event(
                            Resource.Error(
                                getApplication<MyApplication>().getString(
                                    R.string.conversion_error
                                )
                            ))
                    )
                }
            }
        }
    }

    private fun handleOtpResponse(response: Response<LoginResponse>): Event<Resource<LoginResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

}