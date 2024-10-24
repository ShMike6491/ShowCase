package org.example.showcase.common.domain

import kotlin.math.pow
import kotlin.math.roundToInt


fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}