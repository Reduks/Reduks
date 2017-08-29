package com.redust.redust.middleware

import com.redust.redust.Action

interface Middleware<State> {
    fun compose(reducer: (State, Action) -> State) : (State, Action) -> State
}