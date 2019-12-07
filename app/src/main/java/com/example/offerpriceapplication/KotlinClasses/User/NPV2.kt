package com.example.offerpriceapplication.KotlinClasses.User

import android.util.Log
import com.example.offerpriceapplication.Activities.MainActivity
import com.example.offerpriceapplication.Activities.SettingsActivity
import com.example.offerpriceapplication.KotlinClasses.Piramal.df
import com.example.offerpriceapplication.KotlinClasses.Piramal.getLastInstalmentIndex
import com.example.offerpriceapplication.KotlinClasses.Piramal.piramalDates
import com.example.offerpriceapplication.KotlinClasses.Piramal.rateOfinterests
import java.util.concurrent.TimeUnit
import kotlin.math.pow

var nMillies: Long = 0
var n: Double = 0.0
var userInterestSum: Double = 0.0

fun calculateUserInterests(): Double {
    userInterestSum = 0.0
    for (i in 0 until 17) {
        if (getUserPercent(i) > 0.0) {
            nMillies =
                simpleDateFormat.parse(piramalDates[getLastInstalmentIndex()]).time - simpleDateFormat.parse(
                    userDates[i]
                ).time
            n = df.format(TimeUnit.DAYS.convert(nMillies, TimeUnit.MILLISECONDS) / 365.0).toDouble()
            Log.e(
                "npv2",
                "${i + 1} ${getUserPercent(i) / 100 * MainActivity.Companion.flatValue * ((1 + SettingsActivity.Companion.preInterest / 100).pow(
                    n
                ))}"
            )

            userInterestSum += df.format(
                getUserPercent(i) / 100 * MainActivity.Companion.flatValue * ((1 + SettingsActivity.Companion.preInterest / 100).pow(
                    n
                ))
            ).toDouble()

        }
    }
    Log.e("npv2total: ", "$userInterestSum")
    return userInterestSum
}