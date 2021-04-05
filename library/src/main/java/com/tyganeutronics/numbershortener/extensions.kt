package com.tyganeutronics.activator

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.*

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
 *  @param round Boolean
 *  @param suffix String
 *  @param precision Int
 *  @param roundMode Int Round Direction 0 for nearest Integer
 */
fun Number.shorten(
    round: Boolean = true,
    suffix: String = "+",
    precision: Int = 0,
    roundMode: RoundingMode = RoundingMode.HALF_UP,
    units: List<String> = NumberShort.units,
    powers: List<Int> = NumberShort.powers
): String {

    if (units.size != powers.size) {
        throw IllegalArgumentException("Units List needsto be equal to Powers list")
    }

    val ten = 10.0
    val target = BigDecimal(this.toString())

    return when {
        //Does not have a name yet
        //https://www.nist.gov/pml/weights-and-measures/metric-si-prefixes
        target.abs() > ten.pow(27).toBigDecimal() || target.abs() < ten.pow(-27)
            .toBigDecimal() -> target.toPlainString()

        else -> {

            val exp = powers.firstOrNull {
                //@24 start && end => equal
                val start = if (it == 24) 27 else powers[(powers.indexOf(it) - 1).coerceAtLeast(0)]
                val end = it
                ten.pow(start).toBigDecimal() > target && ten.pow(end).toBigDecimal() <= target
            } ?: powers.last()

            val result = (target.div(ten.pow(exp).toBigDecimal()))
                .round(MathContext(if (round) precision else 0, roundMode))

            String.format(
                "%." + result.precision()
                    .coerceAtMost(precision) + "f %c" + if (round) suffix else "",
                result,
                units[powers.indexOf(exp)]
            )
        }
    }
}