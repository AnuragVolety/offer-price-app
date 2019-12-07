package com.example.offerpriceapplication.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.offerpriceapplication.Activities.MainActivity.Companion.flatValue
import com.example.offerpriceapplication.Adapters.OnDateChangeListener
import com.example.offerpriceapplication.Adapters.OnQuantityChangeListener
import com.example.offerpriceapplication.Adapters.RecyclerAdapter
import com.example.offerpriceapplication.Data.InstalmentItem
import com.example.offerpriceapplication.KotlinClasses.Piramal.*
import com.example.offerpriceapplication.KotlinClasses.User.calculateUserInterests
import com.example.offerpriceapplication.KotlinClasses.User.getUserDates
import com.example.offerpriceapplication.KotlinClasses.User.getUserPercent
import com.example.offerpriceapplication.R
import com.example.offerpriceapplication.databinding.ActivityMainBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    val df = DecimalFormat("#########.##")

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private val instalments: ArrayList<InstalmentItem> = ArrayList()

    object Companion {
        @JvmField var flatValue: Long = 0
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager

        adapter = RecyclerAdapter(instalments, object : OnQuantityChangeListener {
            override fun onQuantityChange(change: Boolean) {
                if(change){
                    binding.weightsErrorText.visibility =View.GONE
                    binding.button.isClickable = true
                    binding.button.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    binding.button.setTextColor(Color.BLACK)
                }else{
                    binding.weightsErrorText.visibility =View.VISIBLE
                    binding.button.isClickable = false
                    binding.button.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    binding.button.setTextColor(Color.WHITE)
                }
            }
        }, object : OnDateChangeListener {
            override fun onDateChange(change: Boolean) {
                if(change){
                    binding.datesErrorText.visibility =View.GONE
                    binding.button.isClickable = true
                    binding.button.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    binding.button.setTextColor(Color.BLACK)
                }else{
                    binding.datesErrorText.visibility =View.VISIBLE
                    binding.button.isClickable = false
                    binding.button.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    binding.button.setTextColor(Color.WHITE)
                }
            }
        })
        binding.recyclerView.adapter = adapter


        binding.toolbar.title = ""
        flatValue = binding.flatValue.text.toString().toLong()
        setSupportActionBar(binding.toolbar)
        binding.button.setOnClickListener {
            showDiscount()
        }

        binding.flatValue.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                } catch (e: Exception) {
                }

            }

            override fun afterTextChanged(p0: Editable?) {
                try {
                    flatValue = p0.toString().toLong()
                    binding.toolbar.menu.findItem(R.id.done).isVisible = true
                } catch (e: Exception) {
                }
            }
        })
    }


    private fun loadInstalments(flatValue: Long) {
        instalments.clear()
        var instalment: InstalmentItem
        for (x in 0 until 17) {
            instalment = InstalmentItem(
                x + 1,
                getPiramalPercent(x),
                getPiramalDates(x),
                df.format(flatValue * getPiramalPercent(x) / 100).toDouble(),
                getUserPercent(x),
                getUserDates(x),
                df.format(flatValue * getUserPercent(x) / 100).toDouble(),
                getBufferDays(x)
            )
            instalments.add(instalment)
        }
        adapter.notifyDataSetChanged()
        setNonZeroInstalments()
    }

    private fun showDiscount() {
        setRateOfInterests()
        val intent = Intent(
            this,
            DisplayPriceActivity::class.java
        )
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.done -> {
                item.isVisible = false
                loadInstalments(flatValue)
            }
            R.id.load_profiles -> showProfiles()
            R.id.settings -> openSettings()
        }
        return true
    }

    private fun showProfiles() {
        val intent = Intent(this, AllProfilesActivity::class.java)
        startActivity(intent)
    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("MissingSuperCall")
    override fun onStart() {
        super.onStart()
        if (instalments.size == 0) {
            loadInstalments(flatValue)
        }
    }
}
