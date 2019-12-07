package com.example.offerpriceapplication.KotlinClasses.Piramal

import android.util.Log
import com.example.offerpriceapplication.Activities.MainActivity
import java.util.concurrent.TimeUnit
import kotlin.math.pow

var nMillies: Long = 0
var n: Double = 0.0
var piramalTotalInterestSum: Double = 0.0

fun calculatePiramalInterests(): Double {
    piramalTotalInterestSum = 0.0
    for (i in 0 until 17) {
        if (getPiramalPercent(i) > 0.0) {
            nMillies =
                simpleDateFormat.parse(piramalDates[getLastInstalmentIndex()]).time - simpleDateFormat.parse(
                    piramalDates[i]
                ).time
            n = df.format(TimeUnit.DAYS.convert(nMillies, TimeUnit.MILLISECONDS) / 365.0).toDouble()
            Log.e(
                "npv1",
                "${i + 1} ${getPiramalPercent(i) / 100 * MainActivity.Companion.flatValue * ((1 + rateOfinterests[i] / 100).pow(
                    n
                ))}"
            )
            piramalTotalInterestSum += getPiramalPercent(i) / 100 * MainActivity.Companion.flatValue * ((1 + rateOfinterests[i] / 100).pow(
                n
            ))
        }
    }
    Log.e("npv1total: ", "$piramalTotalInterestSum")
    return piramalTotalInterestSum

}