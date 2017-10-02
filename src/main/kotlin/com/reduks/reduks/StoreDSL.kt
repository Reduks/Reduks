package com.reduks.reduks

import com.reduks.reduks.error.ReduksException

data class StoreDSL<State> (
        var initialState: State?,
        var initialReducer: (State, Action<State>) -> State,
        var enhancer: Enhancer<State>?
) {
    companion object Provider {
        inline fun <reified State> get() : StoreDSL<State> {
            val state = try { State::class.java.newInstance() } catch (e: java.lang.Exception) { throw ReduksException("To create a default initial state, all state fields need to be initialized with a default value.") }
            return StoreDSL(
                    state,
                    { state, action -> action.action(state) },
                    null
            )
        }
    }
}

inline fun <reified State> Any.reduksStore(dsl: StoreDSL<State>.() -> Unit) : Store<State> {
    val storeDsl = StoreDSL.get<State>()
    storeDsl.dsl()
    if (storeDsl.initialState == null) throw ReduksException("You need to pass a default instance to create the store")
    return Store(
            storeDsl.initialState!!,
            storeDsl.initialReducer,
            storeDsl.enhancer
    )
}