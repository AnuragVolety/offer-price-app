package com.example.offerpriceapplication.KotlinClasses.Piramal


var lastInstalmentBoolean = Array<Boolean>(17){true}

fun setNonZeroInstalments(){
    var x=16
    while(x>=0){
        if(piramalPercentages[x]<0.01){
            lastInstalmentBoolean[x] = false

        }
        else{
            break
        }
        x--
    }
}

fun getLastInstalmentIndex():Int{
    var x=16
    while(x>=0){
        if(lastInstalmentBoolean[x]){
            return x
        }
        x--
    }
    return -1
}