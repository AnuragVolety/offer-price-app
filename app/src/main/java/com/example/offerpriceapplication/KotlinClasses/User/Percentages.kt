package com.example.offerpriceapplication.KotlinClasses.User

import kotlin.math.round



var userPercentages = Array<Double>(17) {
    when (it){
        0-> 5.0
        1-> 4.9
        2-> 50.1
        3-> 30.0
        4-> 10.0
        else-> 0.0
    }
}

fun getUserPercent(index: Int): Double {
    return userPercentages[index]
}

fun setUserPercent(index: Int, value: Double) {
    userPercentages[index] = value
}

fun setUserPercentArray(array: Array<Double>) = (0 until 17)
    .forEach { i -> userPercentages[i] = array[i] }

fun setAndCheckUserPercent(index: Int, percentage: Double): Int {
    userPercentages[index] = percentage
    var total: Double = 0.0
    for (i in 0 until 17) {
        total += userPercentages[i]
        if (round(total).toInt() == 100) {
            return i
        }
    }
    return -1
}
