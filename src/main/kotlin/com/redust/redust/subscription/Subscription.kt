package com.redust.redust.subscription

interface Subscription {
    fun unsubscribe()
}

fun Subscription(funktion: () -> Any?) : Subscription = object : Subscription {
    override fun unsubscribe() {
        funktion()
    }
}