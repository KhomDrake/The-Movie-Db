package com.example.themoviedb.common.livedata

class StateData<T>(
    var data: T? = null,
    var error: Throwable? = null,
    var state: State = State.NONE
)

enum class State {
    NONE,
    LOADING,
    ERROR,
    SUCCESS
}