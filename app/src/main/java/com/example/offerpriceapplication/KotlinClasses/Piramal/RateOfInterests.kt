package com.example.offerpriceapplication.KotlinClasses.Piramal

import com.example.offerpriceapplication.Activities.SettingsActivity
import com.example.offerpriceapplication.KotlinClasses.User.getUserDates
import com.example.offerpriceapplication.KotlinClasses.User.simpleDateFormat

var rateOfinterests = Array<Double>(17){ SettingsActivity.Companion.preInterest}

fun setRateOfInterests(){
    for(i in 0 until 17)
        if(simpleDateFormat.parse(getUserDates(i)) > getPirmalDatesWithBuffer(i)){
            rateOfinterests[i] = SettingsActivity.Companion.postInterest
        }
}