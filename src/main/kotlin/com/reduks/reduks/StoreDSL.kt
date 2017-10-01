package com.reduks.reduks

data class StoreDSL<State> (
        var initialState: State? = null,
        var initialReducer: (State, Action<State>) -> State = { state, action -> action.action(state) },
        var enhancer: Enhancer<State>? = null
)

fun <State> Any.reduksStore(dsl: StoreDSL<State>.() -> Unit) : Store<State> {
    val storeDsl = StoreDSL<State>()
    storeDsl.dsl()
    if (storeDsl.initialState == null) throw Exception("You need to pass a default instance to create the store")
    return Store(
            storeDsl.initialState!!,
            storeDsl.initialReducer,
            storeDsl.enhancer
    )
}