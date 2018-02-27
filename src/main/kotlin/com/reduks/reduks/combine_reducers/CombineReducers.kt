package com.reduks.reduks.combine_reducers

import com.reduks.reduks.Action

/**
 * Created by bloder on 18/02/18.
 */

class CombineReducers<State>(private val reducers: Array<out (State, Action<State>) -> State>) {

    fun combine() : (State, Action<State>) -> State = reducers.fold({ state, _ -> state }) { acc, reducer ->
        { state, action -> acc(reducer(state, action), action) }
    }
}

fun <State> Any.combineReducers(vararg reducers: (State, Action<State>) -> State) : (State, Action<State>) -> State = CombineReducers(reducers).combine()