package com.example.themoviedb.common.livedata

class MutableStateLiveData<T> : StateLiveData<T>() {

    fun postData(value: T) {
        stateData.postValue(StateData(data = value, state = State.SUCCESS))
    }

    fun postError(value: Throwable?) {
        stateData.postValue(StateData(error = value, state = State.ERROR))
    }

    fun postLoading(value: Boolean) {
        if(value) stateData.postValue(StateData(state = State.LOADING))
    }
}