package com.reduks.reduks.repository

import com.reduks.reduks.Action
import com.reduks.reduks.Store
import com.reduks.reduks.reduksStore

class FakeData {

    companion object Provider {
        var store = Store(
                FakeState(0, ""),
                { state, action -> action.action(state) }
        )

        val storeWithDslBuilder = reduksStore<FakeState> {}
    }
}

data class FakeState(val id: Int = 0, val name: String = "")
data class MyStateWithDefaultValue(val state: FakeState? = null)
data class StateWithNoDefaultValue(val state: FakeState)

sealed class FakeActions : Action<FakeState> {

    class SetValidState : FakeActions() {
        override fun action(state: FakeState): FakeState = FakeState(1, "Bloder")
    }

    class EmptyAction : FakeActions()
}