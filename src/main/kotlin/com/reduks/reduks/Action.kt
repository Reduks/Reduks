package com.reduks.reduks

interface Action<in State> {
    fun action(state: State)
}