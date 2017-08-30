package com.redust.redust

interface Enhancer<State> {

    fun enhance(reducer: (state: State, action: Action) -> State, store: Store<State>) : (state: State, action: Action) -> State
}