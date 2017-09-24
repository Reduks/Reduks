package com.reduks.reduks

interface Action<State> {
    fun action(state: State) : State = state
}