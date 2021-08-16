package com.example.mvvm.ui.list

import android.view.View
import com.example.mvvm.data.models.MovieSearchList

interface RecycleviewClickListener {
    fun onRecycleViewItemClick(view:View, movie : MovieSearchList, pos:Int)

}