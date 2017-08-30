package com.redust.redust.middleware

import com.redust.redust.Action
import com.redust.redust.Enhancer
import com.redust.redust.Store

class ApplyMiddleware<State>(private val middlewares: List<Middleware<State>>) : Enhancer<State> {

    constructor(vararg middlewares: Middleware<State>) : this(middlewares.asList())

    override fun enhance(reducer: (State, Action) -> State, store: Store<State>): (State, Action) -> State =
        middlewares.fold(reducer) { acc, middleware -> middleware.compose(acc, store) }
}