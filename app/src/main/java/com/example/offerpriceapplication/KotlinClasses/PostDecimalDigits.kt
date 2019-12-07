package com.example.offerpriceapplication.KotlinClasses

var strings = Array<String>(100) { "$it" }
fun getPostDecimalDigits(): Array<String>{
    for (i in 0 until 100){
        if (i<10){
            strings[i] = "0$i"
        }
    }
    return strings
}