package com.example.practiseproject.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.practiseproject.R
import com.example.practiseproject.databinding.ActivityMainBinding
import com.example.practiseproject.utils.AppUtils.isNetworkAvailable
import com.example.practiseproject.utils.NetworkResult
import com.example.practiseproject.viewModel.MainViewModel
import com.google.gson.Gson

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.btnLogin.setOnClickListener(this)
        binding.tvCreateNewAccount.setOnClickListener(this)
        binding.tvForgetPassword.setOnClickListener(this)

    }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_Login -> {
                val emailAddress = binding.edtEmailAddrees.text.toString().trim()
                val userPassword = binding.edtPassword.text.toString().trim()
                viewModel.getLoginRequest(emailAddress, userPassword)
                if (isNetworkAvailable(this)){
                    performLogin(emailAddress, userPassword)
                }else{
                    Toast.makeText(this, "missMatch", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.tv_create_New_account -> {
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
            R.id.tv_forget_password->{
                startActivity(Intent(this,ForgetPasswordActivity::class.java))
                finish()
            }

        }
    }

    private fun performLogin(emailAddress: String, userPassword: String) {
        if (inputsValidation(emailAddress,userPassword)){
            viewModel.loginResponse.observe(this) { networkResult ->
                Log.d("data", Gson().toJson(networkResult))
                networkResult?.let {
                    when (it) {
                        is NetworkResult.Success -> {
                            val intent = Intent(this, DashBoardActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is NetworkResult.Error -> {
                            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
                        }
                        is NetworkResult.Loading -> {
                            Toast.makeText(this, "loading...", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }else{
            Toast.makeText(this, "missMatch", Toast.LENGTH_SHORT).show()
        }
    }
    private fun inputsValidation(userName: String, password: String): Boolean {
        if (TextUtils.isEmpty(userName)) {
            binding.tvUserNameError.text = "Enter name"
            return false
        }
        if (TextUtils.isEmpty(password)) {
            binding.tvPasswordError.text = "Enter the password"
            return false
        }
        if (password.length < 6) {
            binding.tvPasswordError.text = "Password must be at least 6 characters long"
            return false
        }
        return true

    }

}