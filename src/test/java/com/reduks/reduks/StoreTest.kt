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
            print("\nunsubscribed")
            assertTrue(true)
        }
        val subscriber = Subscriber<FakeState> { state ->
            print("\n$state")
            assertTrue(state.name.toLowerCase().trim() == "bloder")
        }

        beforeEach {
            FakeData.store.subscribe(subscriber)
            print("\nsubscribed")
        }

        it("should return a transformed state in subscriber state changed action") {
            FakeData.store.dispatch(FakeActions.SetValidState())
        }

        it("confirm that unsubscribing works") {
            subscription.unsubscribe()
        }
        
    }
})