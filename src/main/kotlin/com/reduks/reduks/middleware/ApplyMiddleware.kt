package com.reduks.reduks.middleware

import com.reduks.reduks.Action
import com.reduks.reduks.Enhancer
import com.reduks.reduks.Store

class ApplyMiddleware<State>(private val middlewares: List<Middleware<State>>) : Enhancer<State> {

    constructor(vararg middlewares: Middleware<State>) : this(middlewares.asList())

    override fun enhance(reducer: (State, Action<State>) -> State, store: Store<State>): (State, Action<State>) -> State =
        middlewares.fold(reducer) { acc, middleware -> middleware.compose(acc, store) }
}