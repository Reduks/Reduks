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

    }

})