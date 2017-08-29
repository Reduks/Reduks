package com.redust.redust

interface Enhancer<State> {

    fun enhance(reducer: (state: State, action: Action) -> State) : (state: State, action: Action) -> State
}