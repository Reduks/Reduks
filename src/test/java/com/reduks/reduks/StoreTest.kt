package com.reduks.reduks

import com.reduks.reduks.repository.FakeActions
import com.reduks.reduks.repository.FakeData
import com.reduks.reduks.repository.FakeState
import com.reduks.reduks.subscription.Subscriber
import com.reduks.reduks.subscription.Subscription
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertTrue

@RunWith(JUnitPlatform::class)
class StoreTest : Spek({

    given("subscriptions") {

        val subscription = Subscription {
            assertTrue(true)
        }

        val subscriber = Subscriber<FakeState> { state ->
            assertTrue(state.name.toLowerCase().trim() == "bloder")
        }

        beforeEach {
            FakeData.store.subscribe(subscriber)
        }

        it("should return a transformed state in subscriber state changed action") {
            FakeData.store.dispatch(FakeActions.SetValidState())
        }

        it("confirm that unsubscribing works") {
            subscription.unsubscribe()
        }

    }

    given("store state") {

        it("should return updated state when I get it from store") {
            FakeData.store.subscribe(Subscriber {})
            FakeData.store.dispatch(FakeActions.SetValidState())
            assertTrue { FakeData.store.getState().name.trim().toLowerCase() == "bloder" }
        }

        it("should return last updated state when call an empty action") {
            FakeData.store.subscribe(Subscriber {})
            FakeData.store.dispatch(FakeActions.SetValidState())
            FakeData.store.dispatch(FakeActions.EmptyAction())
            assertTrue { FakeData.store.getState().name.trim().toLowerCase() == "bloder" }
        }

        it("should return updated state with store in a default reducer implementation") {
            val store = Store(
                    FakeState(0, ""),
                    { state, action -> when(action) {
                        is FakeActions.EmptyAction -> FakeState(0, "")
                        is FakeActions.SetValidState -> FakeState(1, "Bloder")
                        else -> state
                    }}
            )
            store.dispatch(FakeActions.SetValidState())
            assertTrue { store.getState().name.trim().toLowerCase() == "bloder" }
        }

    }

    given("store dsl creation") {

        it("should return correct state when change it value") {
            FakeData.storeWithDslBuilder.subscribe(Subscriber { state -> assertTrue { state.name.trim().toLowerCase() == "bloder" } })
            FakeData.storeWithDslBuilder.dispatch(FakeActions.SetValidState())
        }

        it("should return correct state when it has an action with no logic") {
            FakeData.storeWithDslBuilder.subscribe(Subscriber {})
            FakeData.storeWithDslBuilder.dispatch(FakeActions.SetValidState())
            FakeData.storeWithDslBuilder.dispatch(FakeActions.EmptyAction())
            assertTrue { FakeData.storeWithDslBuilder.getState().name.trim().toLowerCase() == "bloder" }
        }

        it("should return updated state with store dsl in a default reducer implementation") {
            val store = reduksStore<FakeState> {
                initialState = FakeState(0, "")
                initialReducer = { state, action -> when(action) {
                    is FakeActions.SetValidState -> FakeState(1, "Bloder")
                    is FakeActions.EmptyAction -> state
                    else -> state
                }}
            }
            store.subscribe(Subscriber { state -> assertTrue { state.name.trim().toLowerCase() == "bloder" }})
            store.dispatch(FakeActions.SetValidState())
            store.dispatch(FakeActions.EmptyAction())
        }

    }

})