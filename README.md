# Number Shortener

##### A fully customisable number shortening Library for use in android projects

Shortens a given number to at most three digits with an SI unit after

### Samples

1. 987 => 10 h (hecto)
2. 1234 => 1 k (kilo)
3. 123456 => 123 k (kilo)
4. 1234567 => 1 M (mega)

### Usage

#### Kotlin

```kotlin
    val shorter = (123456).shorten()
```

#### Java

```java
    String shorter = NumberShort.INSTANCE.shorten(123456);
```

#### Parameters

All parameters are optional and with kotlin named parameters can be used just to edit the required parameters

```
    round: Boolean              // Whether to round off given number
    suffix: String              // String to add at the end of text after rounding off
    precision: Int              // The digits to keep after decimal comma
    roundMode: RoundingMode     // The mode to use for rounding up numbers
    units: List<String>         // List of units to use after shortening number
    powers: List<Int>           // Powers to use when shortening
```

### Contributing

You are welcome to send push requests (Just remember to add you name to the contributers list) or raise issues

### License

```
 Copyright 2021 Tyganeutronics.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```
