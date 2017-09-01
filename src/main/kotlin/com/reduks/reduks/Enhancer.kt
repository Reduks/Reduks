package com.reduks.reduks

interface Enhancer<State> {

    fun enhance(reducer: (state: State, action: Action<State>) -> State, store: Store<State>) : (state: State, action: Action<State>) -> State
}