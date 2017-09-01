package com.redust.redust.subscription

interface Subscriber<in State> {
    fun stateChanged(state: State)
}

fun <State> Subscriber(funktion: (State) -> Any?) : Subscriber<State> = object : Subscriber<State> {
    override fun stateChanged(state: State) {
        funktion(state)
    }
}