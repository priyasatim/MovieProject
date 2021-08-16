package com.example.mvvm.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.data.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class ViewDetailsFactory(private val repository: MovieRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository) as T
    }
}