package com.reduks.reduks

import com.reduks.reduks.subscription.Subscriber
import com.reduks.reduks.subscription.Subscription

class Store<out State>(initialState: State, initialReducer: (state: State, action: Action<State>) -> State, enhancer: Enhancer<State>? = null) {

    private val subscribers: MutableList<Subscriber<State>> = mutableListOf()
    private var isCurrentDispatching = false
    private val reducer: (state: State, action: Action<State>) -> State = enhancer?.enhance(enhanceReducerWithDispatch(initialReducer), this) ?: enhanceReducerWithDispatch(initialReducer)
    private var state: State = initialState

    fun subscribe(subscriber: Subscriber<State>): Subscription {
        subscribers.add(subscriber)
        return Subscription { subscribers.remove(subscriber) }
    }

    fun dispatch(action: Action<State>) {
        reducer(state, action)
    }

    private fun enhanceReducerWithDispatch(reducer: (state: State, action: Action<State>) -> State): (state: State, action: Action<State>) -> State = { state, action ->
        startDispatching()
        val newState = reducer(state, action)
        stopDispatching()
        notifySubscribers()
        newState
    }

    private fun notifySubscribers() {
        subscribers.forEach { it.stateChanged(state) }
    }

    private fun startDispatching() {
        if (isCurrentDispatching) throw IllegalStateException("You can't dispatch inside a dispatch")
        isCurrentDispatching = true
    }

    private fun stopDispatching() {
        isCurrentDispatching = false
    }
}