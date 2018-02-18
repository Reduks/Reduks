package com.reduks.reduks.combine_reducers

import com.reduks.reduks.Action

/**
 * Created by bloder on 18/02/18.
 */

class CombineReducers<State>(private val reducers: List<(State, Action<State>) -> State>) {

    fun combine() : (State, Action<State>) -> State = fun (initialState: State, action: Action<State>) : State {
        var state: State = initialState
        reducers.forEach {
            val currentState = it(state, action)
            state = if (currentState != state) currentState else state
        }
        return state
    }
}

fun <State> Any.combineReducers(vararg reducers: (State, Action<State>) -> State) : (State, Action<State>) -> State {
    val reducerList = mutableListOf<(State, Action<State>) -> State>()
    reducers.forEach { reducerList.add(it) }
    return CombineReducers(reducerList).combine()
}