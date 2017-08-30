package com.redust.redust

class Store<out State>(initialState: State, initialReducer: (state: State, action: Action) -> State, enhancer: Enhancer<State>? = null) {

    private val subscribers: MutableList<(State) -> Any?> = mutableListOf()
    private var isCurrentDispatching = false
    private val reducer: (state: State, action: Action) -> State = enhancer?.enhance(enhanceReducerWithDispatch(initialReducer)) ?: enhanceReducerWithDispatch(initialReducer)
    private var state: State = initialState

    fun subscribe(subscriber: (State) -> Any?): () -> Any? {
        subscribers.add(subscriber)
        return { subscribers.remove(subscriber) }
    }

    fun dispatch(action: Action) {
        reducer(state, action)
    }

    private fun enhanceReducerWithDispatch(reducer: (state: State, action: Action) -> State): (state: State, action: Action) -> State = { state, action ->
        startDispatching()
        val newState = reducer(state, action)
        stopDispatching()
        notifySubscribers()
        newState
    }

    private fun notifySubscribers() {
        subscribers.forEach { it(state) }
    }

    private fun startDispatching() {
        if (isCurrentDispatching) throw IllegalStateException("You can't dispatch inside a dispatch")
        isCurrentDispatching = true
    }

    private fun stopDispatching() {
        isCurrentDispatching = false
    }

}