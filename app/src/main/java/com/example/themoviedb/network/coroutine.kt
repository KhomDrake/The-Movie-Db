package com.example.themoviedb.network

import com.example.themoviedb.common.livedata.MutableStateLiveData
import com.example.themoviedb.common.livedata.StateLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val job = Job()
private val scope = CoroutineScope(Dispatchers.IO + job)

fun <T> async(func: suspend () -> T) {
    scope.launch { func.invoke() }
}

fun <T> coroutine(
    liveData: MutableStateLiveData<T> = MutableStateLiveData(),
    func: suspend () -> T
) : StateLiveData<T> {
    async {
        try {
            liveData.postLoading(true)
            liveData.postData(func.invoke())
        } catch (e: Exception) {
            liveData.postLoading(false)
            liveData.postError(e)
        }
    }

    return liveData
}

fun coroutine(func: suspend () -> Unit) {
    async {
        try {
            func.invoke()
        } catch (e: Exception) {
        }
    }
}
