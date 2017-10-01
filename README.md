# Reduks

## Introduction
Reduks is an implementation of redux concept for Kotlin applications. It permits to create stores, states, actions and middlwares in an easy way. All redux concept documentation you can easily find in http://redux.js.org/

## Reduks implementation
all reduks components documentation can be easily find in https://github.com/Reduks/Reduks/wiki. <br>
Well, let's develop a hello world! 
<br><br>
First of all we need to create our state.

```kotlin
data class State(val text: String = "")
```
<br>

We want to do a reduks hello world, then in our action we need to return a new state with some string in there and will be "Hello World!", after that we will print this Hello World!. But first lets create our action.

```kotlin
sealed class StateActions : Action<State> {

  class ChangeTextTo(val text: String) : StateActions()
}
```
<br>

Then we just need to create our store.

```kotlin
fun Any.store() : Store<State> = Store(
  State(""),
  { state, action -> when(action) {
    is ChangeTextTo -> State(action.text)
    else -> state
  }}
)
```
<br>

That's great! We already have our reduks structure to print our hello world!

```kotlin
class MainClass {
  
  fun main(args : Array<String>) { 
    store().subscribe(Subscriber { state -> print(state.name) })
    store().dispatch(StateActions.ChangeTextTo("Hello World!"))
  }
}
```

## Inspiration
Reduks was inspired in some other tools, and we'd like to thank you all of them

* React Native - https://facebook.github.io/react-native/

* Redux - http://redux.js.org/

* ReSwift - https://github.com/ReSwift/ReSwift

* redux-kotlin - https://github.com/pardom/redux-kotlin
