package com.reduks.reduks.combine_reducers

import com.reduks.reduks.Action

/**
 * Created by bloder on 18/02/18.
 */

class CombineReducers<State>(private val reducers: Array<out (State, Action<State>) -> State>) {

    fun combine() : (State, Action<State>) -> State = fun (initialState: State, action: Action<State>) : State = reducers.fold(initialState) { acc, reducer ->
        val currentState = reducer(acc, action)
        if (currentState != acc) currentState else acc
    }
}

fun <State> Any.combineReducers(vararg reducers: (State, Action<State>) -> State) : (State, Action<State>) -> State = CombineReducers(reducers).combine()