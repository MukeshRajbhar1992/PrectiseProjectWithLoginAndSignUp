package com.example.practiseproject.repository

import android.util.Log
import com.example.practiseproject.model.LoginRequest
import com.example.practiseproject.model.LoginResponse
import com.example.practiseproject.model.RegistrationRequest
import com.example.practiseproject.model.RegistrationResponse
import com.example.practiseproject.retrofit.ApiClient.apiInterface
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object MainRepository {
    fun getLoginRequest(loginRequest: LoginRequest, callback: (result: LoginResponse) -> Unit){
        CoroutineScope(Dispatchers.IO ).launch {
            val response = apiInterface.getLogin(loginRequest)

            withContext(Dispatchers.Main) {
                response.body()?.let(callback)

            }
        }
    }


    fun getRegistrationRequest(registrationRequest: RegistrationRequest, callback: (result: RegistrationResponse) -> Unit){
        CoroutineScope(Dispatchers.IO ).launch {
            val response = apiInterface.getRegistration(registrationRequest)
            Log.d("TAG","response"+ response)
            withContext(Dispatchers.Main) {
                response.body()?.let(callback)
                Log.d("TAG","responsebody"+Gson().toJson( response.body()))
            }
        }
    }

}