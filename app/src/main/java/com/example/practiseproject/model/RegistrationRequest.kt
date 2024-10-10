package com.example.practiseproject.model


import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("emailAddress")
    val emailAddress: String,
    @SerializedName("fatherLastName")
    val fatherLastName: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("middleName")
    val middleName: String,
    @SerializedName("mobileNumber")
    val mobileNumber: String,
    @SerializedName("motherLastName")
    val motherLastName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("userType")
    val userType: Int=1
)