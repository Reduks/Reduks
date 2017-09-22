package com.reduks.reduks

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
class SpekImplementationTest : Spek({

    given("Spek kotlin dsl builder format") {

        it("should test a correct assertion") {
            assertEquals(5, 2 + 3)
        }

        it("should test an incorrect assertion to make a proposital test breaking") {
            assertEquals(4, 1 + 1)
        }
        
    }

})