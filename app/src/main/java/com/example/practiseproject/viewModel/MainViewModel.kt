package com.example.practiseproject.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practiseproject.MyApplication
import com.example.practiseproject.model.LoginRequest
import com.example.practiseproject.model.LoginResponse
import com.example.practiseproject.model.RegistrationRequest
import com.example.practiseproject.model.RegistrationResponse
import com.example.practiseproject.repository.MainRepository
import com.example.practiseproject.utils.AppUtils.isNetworkAvailable
import com.example.practiseproject.utils.NetworkResult
import com.google.gson.Gson

class MainViewModel : ViewModel() {

    var loginResponse: MutableLiveData<NetworkResult<LoginResponse>> = MutableLiveData()
    var registrationResponse: MutableLiveData<NetworkResult<RegistrationResponse>> = MutableLiveData()

    fun getLoginRequest(emailAddress: String,password: String) {
        if (isNetworkAvailable(MyApplication.instance)) {
            loginResponse.value = NetworkResult.Loading()
            try {
                MainRepository.getLoginRequest(LoginRequest(emailAddress,password)) { apiResult ->

                    when (apiResult.statusCode) {
                        200 -> {
                            loginResponse.value = NetworkResult.Success(apiResult)
                        }

                        else -> {
                            loginResponse.value = NetworkResult.Error("Error in API call")
                        }
                    }
                }
            } catch (e: Exception) {
                loginResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            loginResponse.value = NetworkResult.Error("No Internet connection !")
        }
    }



    fun getRegistrationRequest(emailAddress:String,fatherLastName :String,fistName:String,middleName :String,mobileNumber :String,motherLastName :String,password: String) {
        if (isNetworkAvailable(MyApplication.instance)) {
            registrationResponse.value = NetworkResult.Loading()
            try {
                MainRepository.getRegistrationRequest(RegistrationRequest(emailAddress,fatherLastName,fistName,middleName,mobileNumber,motherLastName,password)) { apiResult ->
                    Log.d("TAG","apiResult"+ apiResult)
                    when (apiResult.statusCode) {
                        200 -> {
                            registrationResponse.value = NetworkResult.Success(apiResult)
                        }

                        else -> {
                            registrationResponse.value = NetworkResult.Error("Error in API call")
                        }
                    }
                }
            } catch (e: Exception) {
                registrationResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            registrationResponse.value = NetworkResult.Error("No Internet connection !")
        }
    }
}