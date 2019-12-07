package com.example.offerpriceapplication.KotlinClasses.Piramal


var bufferDays = Array<Int>(17){0}

fun getBufferDays(index: Int):Int{
    return bufferDays[index]
}

fun setBufferDays(index: Int, value: Int){
    bufferDays[index] = value
}