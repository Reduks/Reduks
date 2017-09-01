package com.reduks.reduks.subscription

interface Subscription {
    fun unsubscribe()
}

fun Subscription(funktion: () -> Any?) : Subscription = object : Subscription {
    override fun unsubscribe() {
        funktion()
    }
}