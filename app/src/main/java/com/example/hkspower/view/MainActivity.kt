package com.example.hkspower.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hkspower.R
import com.example.hkspower.data.api.apis.model.LoginRequest
import com.example.hkspower.data.api.apis.repository.AppRepository
import com.example.hkspower.databinding.ActivityMainBinding
import com.example.hkspower.utils.Resource
import com.example.hkspower.utils.errorSnack
import com.example.hkspower.utils.toast
import com.example.hkspower.view.ui.ViewModelProviderFactory
import com.example.hkspower.view.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding?=null

    lateinit var loginnewViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val repository = AppRepository()
        supportActionBar!!.hide()
        val factory = ViewModelProviderFactory(application, repository)
        loginnewViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        binding!!.backBtn.setOnClickListener {
            finish()
        }
        binding!!.loginBtn.setOnClickListener {
             var userId=binding!!.emailEdt.text.toString()
             var userPass=binding!!.passEdt.text.toString()

             if(userId.equals("")){
                toast("Please enter email id")
             }else if(userPass.equals("")){
                 toast("Please enter password")
             }else{
                 if(isValidEmail(userId)){ // call isValidEmail function and pass email in parameter
                     logindetails(userId,userPass)
                 }else{
                     toast("Please enter valid email id")
                 }

             }

         }

    }
    fun isValidEmail(email: CharSequence): Boolean {
        var isValid = true
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        if (!matcher.matches()) {
            isValid = false
        }
        return isValid
    }
    private fun logindetails(userId: String, userPass: String) {
        if (userId.isNotEmpty()) {
            val body = LoginRequest(
                userId, userPass )

            loginnewViewModel.logDet(body)
            Log.d("TAGData", Gson().toJson(body))
            loginnewViewModel.loginDetails.observe(this, Observer { event ->
                event.getContentIfNotHandled()?.let { response ->
                    when (response) {

                        is Resource.Success -> {
                            hideProgressBar()
                            response.data?.let { loginResponse ->
                                val status: String = loginResponse.status
                                val message: String = loginResponse.messages

                                if (status.equals("true")) {
                                    Log.e("Resopncelogin", message);
                                    val intent1 = Intent(this,DashboardActivity::class.java)
                                    startActivity(intent1)
                                }
                            }
                        }

                        is Resource.Error -> {
                            hideProgressBar()
                            response.message?.let { message ->
                                binding!!.progress.errorSnack(message, Snackbar.LENGTH_LONG)
                            }
                        }

                        is Resource.Loading -> {
                            showProgressBar()
                        }
                    }
                }
            })
        }
    }
    private fun hideProgressBar() {
        binding!!.progress.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding!!.progress.visibility = View.VISIBLE
    }
}