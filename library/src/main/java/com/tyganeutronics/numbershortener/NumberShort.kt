package com.tyganeutronics.activator

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Copyright 2021 Tyganeutronics.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Number Shortening class
 *
 * @author tyganeutronics.com
 * @since 1.0
 */
object NumberShort {

    /**
     * Shorten a given number to:
     *
     *  round = true
     *
     *      4 -> 0+
     *      6 -> 5+
     *      987 -> 900+
     *      1234 -> 1.2 k+
     *      123456 -> 1.2 M+
     *
     *  round = false
     *
     *      987 -> 987
     *      1234 -> 1.2 k
     *      123456 -> 1.2 M
     *
     *  @since 1.0
     *  @author https://tyganeutronics.com
     *  @param number The number to shorten
     *  @param round Boolean
     *  @param suffix String
     *  @param precision Int
     *  @param roundMode Int Round Direction 0 for nearest Integer
     */
    fun shorten(
        number: Number,
        round: Boolean = true,
        suffix: String = "+",
        precision: Int = 0,
        roundMode: RoundingMode = RoundingMode.HALF_UP
    ): String {
        return number.shorten(round, suffix, precision, roundMode)
    }

    /**
     * Shorten a given number to:
     *
     *  round = true
     *
     *      4 -> 0+
     *      6 -> 5+
     *      987 -> 900+
     *      1234 -> 1.2 k+
     *      123456 -> 1.2 M+
     *
     *  round = false
     *
     *      987 -> 987
     *      1234 -> 1.2 k
     *      123456 -> 1.2 M
     *
     *  @since 1.0
     *  @author https://tyganeutronics.com
     *  @param number The number to shorten
     *  @param round Boolean
     *  @param suffix String
     *  @param precision Int
     *  @param roundMode Int Round Direction 0 for nearest Integer
     *  @param units Units
     *  @param powers
     */
    fun shorten(
        number: Number,
        round: Boolean = true,
        suffix: String = "+",
        precision: Int = 0,
        roundMode: RoundingMode = RoundingMode.HALF_UP,
        units: List<String> = NumberShort.units,
        powers: List<Int> = NumberShort.powers
    ): String {
        return number.shorten(round, suffix, precision, roundMode, units, powers)
    }

    /**
     * Shorten number using defaults
     *
     * @param number The number to shorten
     */
    fun shorten(number: Number): String {
        return number.shorten();
    }

    /**
     * Shorten number
     *
     *  @since 1.0
     *  @author https://tyganeutronics.com
     *  @param number The number to shorten
     *  @param precision Int
     *  @param roundMode Int Round Direction 0 for nearest Integer
     */
    fun shorten(
        number: Number,
        precision: Int = 0,
        roundMode: RoundingMode = RoundingMode.HALF_UP
    ): String {
        return number.shorten(precision = precision, roundMode = roundMode);
    }

    /**
     * List of Units
     *
     * Sourced from
     * //https://www.nist.gov/pml/weights-and-measures/metric-si-prefixes
     *
     * @since 1.0
     *  @author https://tyganeutronics.com
     */
    val units: List<String> = listOf(
        "Y",
        "Z",
        "E",
        "P",
        "T",
        "G",
        "M",
        "k",
        "h",
        "da",
        " ",
        "d",
        "c",
        "m",
        "μ",
        "n",
        "p",
        "f",
        "a",
        "z",
        "y"
    )

    /**
     * Corresponding list of powers
     *
     * @since 1.0
     *  @author https://tyganeutronics.com
     */
    val powers: List<Int> = emptyList<Int>().plus(24 downTo 3 step 3).plus(2 downTo -2)
        .plus(-3 downTo -24 step 3)

}