package com.example.mvvm.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.data.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: MovieRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(repository) as T
    }
}