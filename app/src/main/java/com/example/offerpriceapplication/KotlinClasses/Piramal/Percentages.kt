package com.example.offerpriceapplication.KotlinClasses.Piramal

import java.text.DecimalFormat
import kotlin.math.round

val df = DecimalFormat("##.##")

var piramalPercentages = Array<Double>(17) {
    when (it){
        0-> 4.0
        1-> 5.9
        2-> 10.1
        3-> 10.0
        4-> 15.0
        5-> 10.0
        6-> 5.0
        7-> 5.0
        8-> 5.0
        9-> 5.0
        10-> 5.0
        11-> 5.0
        12-> 10.0
        13-> 5.0
        else-> 0.0
    }
}


fun getPiramalPercent(index: Int): Double {
    return piramalPercentages[index]
}

fun setPiramalPercent(index: Int, value: Double) {
    piramalPercentages[index] = value
}

fun setPiramalPercentArray(array: Array<Double>) = (0 until 17)
    .forEach { i -> piramalPercentages[i] = array[i] }


fun setAndCheckPiramalPercent(index: Int, percentage: Double): Int {
    piramalPercentages[index] = percentage
    var total = 0.0
    for (i in 0 until 17) {
        total += piramalPercentages[i]
        if (round(total).toInt() == 100) {
            return i
        }
    }
    return -1
}
