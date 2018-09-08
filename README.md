# Reduks

## Introduction
Reduks is an implementation of redux concept for Kotlin applications. It permits to create stores, states, actions and middlwares in an easy way. All redux concept documentation you can easily find in http://redux.js.org/

## Reduks implementation
all reduks components documentation can be easily find in https://github.com/Reduks/Reduks/wiki <br>
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
fun store() : Store<State> = reduksStore {
  initialState = State(""),
  initialReducer = { state, action -> when(action) {
    is ChangeTextTo -> State(action.text)
    else -> state
  }}
}
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

## Import

##### Gradle 
```groovy
implementation 'com.reduks:reduks:0.1.3'
```

##### Maven
```xml
<dependency>
  <groupId>com.reduks</groupId>
  <artifactId>reduks</artifactId>
  <version>0.1.3</version>
  <type>pom</type>
</dependency>
```

##### Ivy
```xml
<dependency org='com.reduks' name='reduks' rev='0.1.3'>
  <artifact name='reduks' ext='pom' ></artifact>
</dependency>
```

## Inspiration
Reduks was inspired in some other tools, and we'd like to thank all of them

* Redux - http://redux.js.org/

* ReSwift - https://github.com/ReSwift/ReSwift

* redux-kotlin - https://github.com/pardom/redux-kotlin

## License

```
MIT License

Copyright (c) 2017 Reduks

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
