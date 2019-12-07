package com.example.offerpriceapplication.KotlinClasses.Piramal

import java.util.*

var calendar:Calendar = Calendar.getInstance()


var piramalDatesWithBuffer = Array<Date> (17) { simpleDateFormat.parse(piramalDates[it]) }

fun setPirmalDatesWithBuffer(){
    for(i in 0 until 17){
        addDaysToDate(simpleDateFormat.parse(piramalDates[i]), getBufferDays(i), i)
    }
}

fun addDaysToDate(date: Date, i: Int, index: Int) {
    calendar.time = date
    calendar.add(Calendar.DATE,i)
    piramalDatesWithBuffer[index] = calendar.time
}

fun getPirmalDatesWithBuffer(index: Int): Date{
    return piramalDatesWithBuffer[index]
}

