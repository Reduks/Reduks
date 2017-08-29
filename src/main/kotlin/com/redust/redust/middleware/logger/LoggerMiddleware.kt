package com.redust.redust.middleware.logger

import com.redust.redust.Action
import com.redust.redust.middleware.Middleware

class LoggerMiddleware<State>(private val thirdPartLogger: (String) -> Any? = { java.util.logging.Logger.getGlobal().info(it) }) : Middleware<State> {
    override fun compose(reducer: (State, Action) -> State): (State, Action) -> State {
        return { state, action ->
            thirdPartLogger.invoke("Reducing: ${state.toString()} with action: ${action.javaClass.name}")
            val result = reducer(state, action)
            thirdPartLogger.invoke("Reducing: ${state.toString()} with action: ${action.javaClass.name}")
            result
        }
    }
}