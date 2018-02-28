package com.reduks.reduks

import com.reduks.reduks.combine_reducers.combineReducers
import com.reduks.reduks.repository.FakeState
import com.reduks.reduks.subscription.Subscriber
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertTrue


@RunWith(JUnitPlatform::class)
class CombinerReducersTest : Spek({

    given("Combine reducer flow") {

        val reducer1 : (FakeState, Action<FakeState>) -> FakeState = { state, action -> when(action) {
            is Action1.Daniel -> FakeState(0, "Daniel")
            is Action1.Bloder -> FakeState(0, "Bloder")
            else -> state
        }}

        val reducer2 : (FakeState, Action<FakeState>) -> FakeState = { state, action -> when(action) {
            is Action2.Vitor -> FakeState(0, "Vitor")
            is Action2.Prado -> FakeState(0, "Prado")
            else -> state
        }}

        val reducer3 : (FakeState, Action<FakeState>) -> FakeState = { state, action -> when(action) {
            is Action3.Test1 -> FakeState(0, "Test1")
            is Action3.Test2 -> FakeState(0, "Test2")
            else -> state
        }}

        val reducer4 : (FakeState, Action<FakeState>) -> FakeState = { state, action -> when(action) {
            is Action4.Increase -> FakeState(state.id + 1, state.name)
            else -> state
        }}

        val reducer5 : (FakeState, Action<FakeState>) -> FakeState = { state, action -> when(action) {
            is Action4.Increase -> FakeState(state.id + 2, state.name)
            else -> state
        }}

        it("should assert reducers merging") {
            val store = Store(FakeState(0, "Default"), combineReducers(reducer1, reducer2, reducer3))
            store.subscribe(Subscriber { println(it.name) })
            store.dispatch(Action3.Test1())
            assertTrue { store.getState().name == "Test1" }
        }

        it("should assert that combiner reducer will iterate correctly in reducers with same action") {
            val store = Store(FakeState(0, ""), combineReducers(reducer4, reducer5))
            store.subscribe(Subscriber { state -> assertTrue { state.id == 3 } })
            store.dispatch(Action4.Increase())
        }
    }
})

sealed class Action1 : Action<FakeState> {

    class Daniel : Action1()
    class Bloder : Action1()
}

sealed class Action2 : Action<FakeState> {

    class Vitor : Action2()
    class Prado : Action2()
}

sealed class Action3 : Action<FakeState> {

    class Test1 : Action3()
    class Test2 : Action3()
}

sealed class Action4 : Action<FakeState> {

    class Increase : Action4()
}