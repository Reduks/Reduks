package com.reduks.reduks.middleware

import com.reduks.reduks.Action
import com.reduks.reduks.Store

interface Middleware<State> {
    fun compose(reducer: (State, Action<State>) -> State, store: Store<State>) : (State, Action<State>) -> State
}