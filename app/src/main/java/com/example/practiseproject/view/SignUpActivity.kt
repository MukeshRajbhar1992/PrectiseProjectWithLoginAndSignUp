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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.practiseproject.R
import com.example.practiseproject.databinding.ActivitySignUpBinding
import com.example.practiseproject.utils.NetworkResult
import com.example.practiseproject.viewModel.MainViewModel
import com.google.gson.Gson

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        binding.btnRegister.setOnClickListener(this)
        binding.tvAllReadyAccount.setOnClickListener(this)

    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_register -> {

                val firstName = binding.edtUserName.text.toString().trim()
                val middleName = binding.edtMiddleName.text.toString().trim()
                val fatherLastName = binding.edtFatherLastName.text.toString().trim()
                val motherLastName = binding.edtMotherLastName.text.toString().trim()
                val emailAddress = binding.edtEmailAddrees.text.toString().trim()
                val mobileNumber = binding.edtMobileNumber.text.toString().trim()
                val password = binding.edtSignPassword.text.toString().trim()
                val confirmPassword = binding.edtSignConfirmPassword.text.toString().trim()

                Log.d("firstName",Gson().toJson(firstName))
                viewModel.getRegistrationRequest(
                        emailAddress,
                        fatherLastName,
                        firstName,
                        middleName,
                        mobileNumber,
                        motherLastName,
                        password
                    )
                performRegistration(
                    emailAddress,
                    fatherLastName,
                    firstName,
                    middleName,
                    mobileNumber,
                    motherLastName,
                    password,
                    confirmPassword
                )
            }

            R.id.tv_all_ready_Account -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }

    }

    private fun performRegistration(
        emailAddress: String,
        fatherLastName: String,
        firstName: String,
        middleName: String,
        mobileNumber: String,
        motherLastName: String,
        password: String,
        confirmPassword: String
    ) {

        if (
            inputsValidationForSignUp(
                emailAddress,
                fatherLastName,
                firstName,
                middleName,
                mobileNumber,
                motherLastName,
                password,
                confirmPassword
            )
        ) {
            viewModel.registrationResponse.observe(this) { networkResult ->
                Log.d("data", Gson().toJson(networkResult))
                networkResult?.let {
                    when (it) {
                        is NetworkResult.Success -> {
                            val intent = Intent(this, LoginActivity::class.java)
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

        } else {
            Toast.makeText(this, "missMatch", Toast.LENGTH_SHORT).show()
        }


    }

    private fun inputsValidationForSignUp(
        emailAddress: String,
        fatherLastName: String,
        firstName: String,
        middleName: String,
        mobileNumber: String,
        motherLastName: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (TextUtils.isEmpty(firstName)) {
            binding.userNameError.text = "Enter name"
            return false
        }
        if (TextUtils.isEmpty(middleName)) {
            binding.middleNameError.text = "Enter middle name"
            return false
        }
        if (TextUtils.isEmpty(fatherLastName)) {
            binding.fatherLastNameError.text = "Enter father last name"
            return false
        }
        if (TextUtils.isEmpty(motherLastName)) {
            binding.motherLastNameError.text = "Enter mother last name"
            return false
        }
        if (TextUtils.isEmpty(emailAddress)) {
            binding.emailAddressError.text = "Enter email address"
            return false
        }

        if (TextUtils.isEmpty(mobileNumber)) {
            binding.mobileNumberError.text = "Enter mobile number"
            return false
        }

        if (TextUtils.isEmpty(password)) {
            binding.passwordError.text = "Enter password"
            return false
        }
        if (password.length < 6) {
            binding.passwordError.text = "Password must be at least 6 characters long"
            return false
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            binding.confirmPasswordError.text = "Enter confirm password"
            return false
        }
        if (confirmPassword.length < 6) {
            binding.confirmPasswordError.text = "Password must be at least 6 characters long"
            return false
        }
        return true

    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}