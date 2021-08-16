package com.example.mvvm.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.util.Coroutine
import com.example.mvvm.data.models.MovieSearchList
import com.example.mvvm.data.repository.MovieRepository
import kotlinx.coroutines.Job

class MovieViewModel(private val repository: MovieRepository) :ViewModel(){

    lateinit var job:Job
    private val _movie = MutableLiveData<List<MovieSearchList>>()
    val movie :LiveData<List<MovieSearchList>>
    get() = _movie

     fun getMovie(){
        job = Coroutine.ioThreadMain(
            { repository.getMovie("1")
            },{
                _movie.value = it?.Search
            })

     }


    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}