package com.example.offerpriceapplication.KotlinClasses.User

import java.text.SimpleDateFormat
import java.util.*

const val pattern = "dd MMM yy"
val simpleDateFormat = SimpleDateFormat(pattern)
var c1= Calendar.getInstance()

var userDates = Array<String> (17) {
    c1.add(Calendar.DAY_OF_YEAR, it*10)
    simpleDateFormat.format(c1.time)
}

fun getUserDates(index: Int):String{
    return userDates[index]
}

fun setUserDates(index: Int, value: String){
    userDates[index] = value
}

fun setUserDatesArray(array: Array<String>) = (0 until 17)
    .forEach { i -> userDates[i] = array[i] }

fun setAndCheckUserDates(index: Int, value: String): Boolean{
    userDates[index] = value
    if(index>0){
        val previousDate:Date = simpleDateFormat.parse(userDates[index-1])
        if(index<16)
        {
            val nextDate: Date = simpleDateFormat.parse(userDates[index+1])
            if(simpleDateFormat.parse(userDates[index]).compareTo(nextDate) > 0){
                return false
            }
            if(previousDate.compareTo(simpleDateFormat.parse(userDates[index])) > 0){
                return false
            }
            return true
        }

    }
    else{
        val nextDate: Date = simpleDateFormat.parse(userDates[index+1])
        if(simpleDateFormat.parse(userDates[index]).compareTo(nextDate) > 0){
            return false
        }
        return true
    }
    return true
}