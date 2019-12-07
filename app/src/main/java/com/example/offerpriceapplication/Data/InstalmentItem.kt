package com.example.offerpriceapplication.Data


class InstalmentItem(
    instalmentNo: Int,
    piramalPercent: Double, piramalDate: String, piramalDueMoney: Double,
    userPercent: Double, userDate: String, userDueMoney: Double,
    buffer: Int){
    var instalmentNo: Int
    var piramalPercent: Double
    var piramalDate: String
    var piramalDueMoney: Double
    var userPercent: Double
    var userDate: String
    var userDueMoney: Double
    var buffer: Int

    init {
        this.instalmentNo = instalmentNo
        this.piramalPercent = piramalPercent
        this.piramalDate = piramalDate
        this.piramalDueMoney = piramalDueMoney
        this.userPercent = userPercent
        this.userDate = userDate
        this.userDueMoney = userDueMoney
        this.buffer = buffer
    }

}
