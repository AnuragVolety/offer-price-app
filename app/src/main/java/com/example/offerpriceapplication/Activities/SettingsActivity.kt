package com.example.offerpriceapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import com.example.offerpriceapplication.R
import com.example.offerpriceapplication.databinding.ActivitySettingsBinding
import org.w3c.dom.Text

class SettingsActivity : AppCompatActivity() {
    object Companion{
        @JvmField var preInterest: Double = 8.0
        @JvmField var postInterest: Double = 12.5
    }

    var tempPre = Companion.preInterest
    var tempPost = Companion.postInterest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val binding = DataBindingUtil.setContentView<ActivitySettingsBinding>(this, R.layout.activity_settings)

        binding.preInterest.setText(Companion.preInterest.toString())
        binding.postInterest.setText(Companion.postInterest.toString())

        binding.preInterest.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                try {
                } catch (e: Exception) {
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                } catch (e: Exception) {
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    tempPre = p0.toString().toDouble()
                    if(tempPre == Companion.preInterest){
                        binding.saveButton.setColorFilter(resources.getColor(R.color.colorAccent))
                    }
                    else{
                        binding.saveButton.setColorFilter(resources.getColor(R.color.colorPrimary))
                    }
                } catch (e: Exception) {
                }
            }

        })
        binding.postInterest.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                try {
                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                } catch (e: Exception) {
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    tempPost = p0.toString().toDouble()
                    if(tempPost == Companion.postInterest){
                        binding.saveButton.setColorFilter(resources.getColor(R.color.colorAccent))
                    }
                    else{
                        binding.saveButton.setColorFilter(resources.getColor(R.color.colorPrimary))
                    }
                } catch (e: Exception) {
                }
            }

        })

        binding.saveButton.setOnClickListener {
            if(tempPost!=Companion.postInterest || tempPre!=Companion.preInterest){
                if(tempPost!=Companion.postInterest){
                    Companion.postInterest = tempPost
                }
                if(tempPre!=Companion.preInterest){
                    Companion.preInterest = tempPre
                }
                finish()
            }
        }
    }
}
