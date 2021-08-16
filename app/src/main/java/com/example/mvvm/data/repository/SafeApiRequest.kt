package com.example.mvvm.data

import android.util.Log
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException


abstract class SafeApiRequest {
    var currentPage = 1

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>):T{
        val response = call.invoke()
        if(response.isSuccessful){
            currentPage++
            return response.body()!!

        }else{
            //@todo handle api exception
            throw ApiException(response.code().toString())
        }
    }
}

class ApiException(message: String) :IOException(message)