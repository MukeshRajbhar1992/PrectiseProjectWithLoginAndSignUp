package com.example.practiseproject.retrofit

import com.example.practiseproject.model.LoginRequest
import com.example.practiseproject.model.LoginResponse
import com.example.practiseproject.model.RegistrationRequest
import com.example.practiseproject.model.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

//    @GET("/3/movie/popular?")
//    suspend fun getMovies(@Query("api_key") key: String): Response<PopularMovieResponse>
//
//    @GET("/3/tv/popular?")
//    suspend fun getTVShows(@Query("api_key") key: String): Response<TvShowResponse>
//
//    @GET("/3/person/popular?")
//    suspend fun getPerson(@Query("api_key") key:String) : Response<PopularPersonResponse>


    @POST("/api/Login")
    suspend fun getLogin(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("/api/UserRegistration")
    suspend fun getRegistration(
        @Body registrationRequest: RegistrationRequest
    ): Response<RegistrationResponse>
}