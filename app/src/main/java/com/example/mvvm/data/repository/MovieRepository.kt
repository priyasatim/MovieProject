package com.example.mvvm.data.repository

import android.util.Log
import com.example.mvvm.data.SafeApiRequest
import com.example.mvvm.data.network.MovieApi

class MovieRepository(private val api : MovieApi) : SafeApiRequest(){
    suspend fun getMovie(page:String) = apiRequest { api.getMovies("e5311742","Batman",page) }
    suspend fun getDetails(i:String) = apiRequest { api.getDetails("e5311742",i) }
      }
