package com.example.mvvm.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.data.models.MovieDetails
import com.example.mvvm.data.repository.MovieRepository
import com.example.mvvm.util.Coroutine
import kotlinx.coroutines.Job

class DetailsViewModel(private val repository: MovieRepository) : ViewModel(){

    lateinit var job: Job
    private val _movie = MutableLiveData<MovieDetails>()
    val details : LiveData<MovieDetails>
        get() = _movie

    fun getDetails(id: String){
        job = Coroutine.ioThreadMain(
            {
                 repository.getDetails(id)
            },{
                _movie.value = it
            })

    }


    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}