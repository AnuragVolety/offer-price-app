package com.example.offerpriceapplication.KotlinClasses.Piramal

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

const val pattern = "dd MMM yy"
val simpleDateFormat = SimpleDateFormat(pattern)
var c= Calendar.getInstance()

var piramalDates = Array<String> (17) {
    c.add(Calendar.DAY_OF_YEAR, it*10)
    simpleDateFormat.format(c.time)
}


fun getPiramalDates(index: Int):String{
    return piramalDates[index]
}

fun setPiramalDates(index: Int, value: String){
    piramalDates[index] = value
}


fun setAndCheckPiramalDates(index: Int, value: String): Boolean{
    piramalDates[index] = value
    if(index>0){
        val previousDate:Date = simpleDateFormat.parse(piramalDates[index-1])
        if(index<16)
        {
            val nextDate: Date = simpleDateFormat.parse(piramalDates[index+1])
            if(simpleDateFormat.parse(piramalDates[index]).compareTo(nextDate) > 0){
                return false
            }
            if(previousDate.compareTo(simpleDateFormat.parse(piramalDates[index])) > 0){
                return false
            }
            return true
        }

    }
    else{
        val nextDate: Date = simpleDateFormat.parse(piramalDates[index+1])
        if(simpleDateFormat.parse(piramalDates[index]).compareTo(nextDate) > 0){
            return false
        }
        return true
    }
    return true
}