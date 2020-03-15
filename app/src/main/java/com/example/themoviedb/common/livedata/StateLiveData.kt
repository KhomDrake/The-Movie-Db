package com.example.themoviedb.common.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

abstract class StateLiveData<T> : LiveData<T>() {

    protected val stateData: MutableLiveData<StateData<T>> = MutableLiveData()

    fun observeData(owner: LifecycleOwner, observer: (T) -> Unit) : StateLiveData<T> {
        stateData.observe(owner, Observer { stateData ->
            stateData?.data?.let {
                if(stateData.state == State.SUCCESS)
                    observer.invoke(it)
            }
        })

        return this
    }

    fun observeLoading(owner: LifecycleOwner, observer: (Boolean) -> Unit) : StateLiveData<T> {
        stateData.observe(owner, Observer {
            it?.let {
                if(it.state == State.LOADING)
                    observer.invoke(true)
                else
                    observer.invoke(false)
            }
        })

        return this
    }

    fun observeError(owner: LifecycleOwner, observer: (Throwable) -> Unit) : StateLiveData<T> {
        stateData.observe(owner, Observer { stateData ->
            stateData?.error?.let {
                if(stateData.state == State.ERROR)
                    observer.invoke(it)
            }
        })

        return this
    }
}

