package com.redust.redust.middleware

import com.redust.redust.Action
import com.redust.redust.Store

interface Middleware<State> {
    fun compose(reducer: (State, Action) -> State, store: Store<State>) : (State, Action) -> State
}