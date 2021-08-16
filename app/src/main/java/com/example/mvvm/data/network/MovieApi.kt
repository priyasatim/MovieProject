package com.example.mvvm.data.network

import com.example.mvvm.data.models.MovieApiResponse
import com.example.mvvm.data.models.MovieDetails
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/")
    suspend fun getMovies(@Query("apikey")apikey :String,@Query("s")s :String,@Query("page")page :String) : Response<MovieApiResponse>

    @GET("/")
    suspend fun getDetails(@Query("apikey")apikey :String,@Query("i")i :String): Response<MovieDetails>

    companion object{
    operator fun invoke() : MovieApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.omdbapi.com")
            .build()
            .create(MovieApi::class.java)
    }
}
}