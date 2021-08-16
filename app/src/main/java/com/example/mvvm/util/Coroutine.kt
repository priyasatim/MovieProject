package com.example.mvvm.util

import kotlinx.coroutines.*

object Coroutine {
    fun<T :Any> ioThreadMain(work:suspend (() -> T?),callback: ((T?)-> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async {
                return@async work()
            }.await()
            callback(data)
    }
}