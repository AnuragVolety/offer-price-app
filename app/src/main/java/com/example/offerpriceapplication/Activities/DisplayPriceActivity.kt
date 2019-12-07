package com.example.offerpriceapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.offerpriceapplication.KotlinClasses.Piramal.calculatePiramalInterests
import com.example.offerpriceapplication.KotlinClasses.Piramal.df
import com.example.offerpriceapplication.KotlinClasses.User.calculateUserInterests
import com.example.offerpriceapplication.R
import com.example.offerpriceapplication.databinding.ActivityDisplayPriceBinding

class DisplayPriceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDisplayPriceBinding>(this,
            R.layout.activity_display_price
        )
        var discount = calculatePiramalInterests() / calculateUserInterests() *100
        var discountedMoney = (MainActivity.Companion.flatValue*(1-discount/100))
        if(discount>100){
            binding.discountAvailedText.visibility = View.GONE
            binding.discountMoney.visibility = View.GONE
            binding.discountPercent.visibility = View.GONE
        }
        else{
            binding.discountPercent.text = "(${df.format(100-discount)})%"
            binding.discountMoney.text = String.format("%.2f",discountedMoney)
        }
        binding.offerPrice.text = String.format("%.2f",MainActivity.Companion.flatValue - discountedMoney)
    }
}
