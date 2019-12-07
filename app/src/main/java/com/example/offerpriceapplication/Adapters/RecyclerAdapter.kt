package com.example.offerpriceapplication.Adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.view.View.inflate
import android.widget.NumberPicker
import com.example.offerpriceapplication.*
import com.example.offerpriceapplication.Activities.MainActivity
import com.example.offerpriceapplication.Data.InstalmentItem
import com.example.offerpriceapplication.KotlinClasses.Piramal.*
import com.example.offerpriceapplication.KotlinClasses.User.getUserPercent
import com.example.offerpriceapplication.KotlinClasses.User.setAndCheckUserDates
import com.example.offerpriceapplication.KotlinClasses.User.setAndCheckUserPercent
import com.example.offerpriceapplication.KotlinClasses.User.setUserDates
import com.example.offerpriceapplication.KotlinClasses.getMonthName
import com.example.offerpriceapplication.KotlinClasses.getPostDecimalDigits
import com.example.offerpriceapplication.KotlinClasses.inflate
import java.math.BigDecimal
import java.text.DecimalFormat

public interface OnQuantityChangeListener {
    fun onQuantityChange(change: Boolean)
}

public interface OnDateChangeListener {
    fun onDateChange(change: Boolean)
}

class RecyclerAdapter(private val instalments: ArrayList<InstalmentItem>, private val listener: OnQuantityChangeListener,
                      private val dateListener: OnDateChangeListener) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = parent.inflate(R.layout.instalment_list_item, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount() = instalments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = instalments[position]
        holder.bindInstalment(item, listener, dateListener)
    }

    class ViewHolder(
        v: View) : RecyclerView.ViewHolder(v) {
        val pattern = "dd MMM yy"
        val simpleDateFormat = SimpleDateFormat(pattern)

        var instalmentNo: TextView = v.findViewById(R.id.instalment_no)

        var piramalPercent: TextView = v.findViewById(R.id.piramal_percent)
        var piramalDate: TextView = v.findViewById(R.id.piramal_date)
        var piramalDueMoney: TextView = v.findViewById(R.id.piramal_due_money)

        var userPercent: TextView = v.findViewById(R.id.user_percent)
        var userDate: TextView = v.findViewById(R.id.user_date)
        var userDueMoney: TextView = v.findViewById(R.id.user_due_money)

        var buffer: TextView = v.findViewById(R.id.buffer)

        @SuppressLint("ClickableViewAccessibility")
        fun bindInstalment(
            instalmentItem: InstalmentItem,
            mListener: OnQuantityChangeListener,
            dateListener: OnDateChangeListener
        ) {
            this.instalmentNo.text = instalmentItem.instalmentNo.toString()

            this.piramalPercent.text = instalmentItem.piramalPercent.toString() + "%"
            this.piramalDate.text = instalmentItem.piramalDate
            this.piramalDueMoney.text = String.format("%.1f",instalmentItem.piramalDueMoney)

            this.userPercent.text = instalmentItem.userPercent.toString() + "%"
            this.userDate.text = instalmentItem.userDate
            this.userDueMoney.text = String.format("%.1f",instalmentItem.userDueMoney)

            this.buffer.text = instalmentItem.buffer.toString()

            this.piramalDate.setOnClickListener { view ->
                val cal = Calendar.getInstance()
                cal.time = simpleDateFormat.parse(this.userDate.text.toString())
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(
                    view.context,
                    R.style.Dialog_Theme,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        //Check if dates are in increasing order
                        dateListener.onDateChange(setAndCheckPiramalDates(
                            instalmentItem.instalmentNo - 1,
                            "$dayOfMonth ${getMonthName(
                                monthOfYear
                            )} ${year - 2000}"
                        ))


                        this.piramalDate.text = "$dayOfMonth ${getMonthName(
                            monthOfYear
                        )} ${year - 2000}"


                    }, year, month, day
                )

                dpd.show()
            }

            this.userDate.setOnClickListener { view ->
                val cal = Calendar.getInstance()
                cal.time = simpleDateFormat.parse(this.userDate.text.toString())
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(
                    view.context,
                    R.style.Dialog_Theme,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        //Check if dates are in increasing order
                        dateListener.onDateChange(setAndCheckUserDates(
                            instalmentItem.instalmentNo - 1,
                            "$dayOfMonth ${getMonthName(
                                monthOfYear
                            )} ${year - 2000}"
                        ))
                        this.userDate.text = "$dayOfMonth ${getMonthName(
                            monthOfYear
                        )} ${year - 2000}"
                    }, year, month, day
                )

                dpd.show()
            }

            this.piramalPercent.setOnClickListener { view ->
                val builder = AlertDialog.Builder(view.context)
                val dialog = inflate(
                    view.context,
                    R.layout.number_picker_dialog_box, null
                )

                val dialogText = dialog.findViewById<TextView>(R.id.dialog_text)
                dialogText.text =
                    "Select Piramal Payment Schedule for Instalment ${this.instalmentNo.text}"

                val preDecimal = dialog.findViewById<NumberPicker>(R.id.pre_decimal_picker)
                preDecimal.minValue = 0
                preDecimal.maxValue = 99
                preDecimal.value =
                    this.piramalPercent.text.toString().dropLast(1).toDouble().toInt()

                val postDecimal = dialog.findViewById<NumberPicker>(R.id.post_decimal_picker)

                postDecimal.displayedValues =
                    getPostDecimalDigits()
                postDecimal.minValue = 0
                postDecimal.maxValue = 99
                postDecimal.value = ((this.piramalPercent.text.toString().dropLast(1).toDouble()
                        - this.piramalPercent.text.toString().dropLast(1).toDouble().toInt().toDouble())
                        * 100).toInt()


                builder.setView(dialog)
                    .setPositiveButton("DONE",
                        DialogInterface.OnClickListener { dialog1, which ->
                            var index: Int
                            when {
                                postDecimal.value == 0 -> {
                                    //Check if weights sum to 100
                                    index = setAndCheckPiramalPercent(
                                        instalmentItem.instalmentNo - 1,
                                        "${preDecimal.value}.00".toDouble()
                                    )
                                    this.piramalPercent.text = "${preDecimal.value}%"
                                }
                                postDecimal.value < 10 -> {
                                    //Check if weights sum to 100
                                    index = setAndCheckPiramalPercent(
                                        instalmentItem.instalmentNo - 1,
                                        "${preDecimal.value}.0${postDecimal.value}".toDouble()
                                    )
                                    this.piramalPercent.text =
                                        "${preDecimal.value}.0${postDecimal.value}%"
                                }
                                else -> {
                                    //Check if weights sum to 100
                                    index = setAndCheckPiramalPercent(
                                        instalmentItem.instalmentNo - 1,
                                        "${preDecimal.value}.${postDecimal.value}".toDouble()
                                    )
                                    this.piramalPercent.text =
                                        "${preDecimal.value}.${postDecimal.value}%"
                                }
                            }
                            when {
                                index > -1 -> mListener.onQuantityChange(true)
                                else -> mListener.onQuantityChange(false)
                            }

                            this.piramalDueMoney.text = DecimalFormat("#,##,##,##,###.##")
                                .format(
                                    MainActivity.Companion.flatValue * getPiramalPercent(
                                        instalmentItem.instalmentNo - 1
                                    ) / 100
                                )
                            setNonZeroInstalments()
                        })
                    .setNegativeButton("CANCEL",
                        DialogInterface.OnClickListener { dialog, which ->
                            Log.e("Dialog", "Test Cancel")
                        })

                builder.show()
            }

            this.userPercent.setOnClickListener { view ->
                val builder = AlertDialog.Builder(view.context)
                val dialog = inflate(
                    view.context,
                    R.layout.number_picker_dialog_box, null
                )

                val dialogText = dialog.findViewById<TextView>(R.id.dialog_text)
                dialogText.text =
                    "Select User Payment Schedule for Instalment ${this.instalmentNo.text}"

                val preDecimal = dialog.findViewById<NumberPicker>(R.id.pre_decimal_picker)
                preDecimal.minValue = 0
                preDecimal.maxValue = 99
                preDecimal.value =
                    this.userPercent.text.toString().dropLast(1).toDouble().toInt()

                val postDecimal = dialog.findViewById<NumberPicker>(R.id.post_decimal_picker)

                postDecimal.displayedValues =
                    getPostDecimalDigits()
                postDecimal.minValue = 0
                postDecimal.maxValue = 99
                postDecimal.value = ((this.userPercent.text.toString().dropLast(1).toDouble()
                        - this.userPercent.text.toString().dropLast(1).toDouble().toInt().toDouble())
                        * 100).toInt()

                builder.setView(dialog)
                    .setPositiveButton("DONE",
                        DialogInterface.OnClickListener { dialog1, which ->
                            var index: Int
                            when {
                                postDecimal.value == 0 -> {
                                    //Check if weights sum to 100
                                    index = setAndCheckUserPercent(
                                        instalmentItem.instalmentNo - 1,
                                        "${preDecimal.value}.00".toDouble()
                                    )
                                    this.userPercent.text = "${preDecimal.value}%"
                                }
                                postDecimal.value < 10 -> {
                                    //Check if weights sum to 100
                                    index = setAndCheckUserPercent(
                                        instalmentItem.instalmentNo - 1,
                                        "${preDecimal.value}.0${postDecimal.value}".toDouble()
                                    )
                                    this.userPercent.text =
                                        "${preDecimal.value}.0${postDecimal.value}%"
                                }
                                else -> {
                                    //Check if weights sum to 100
                                    index = setAndCheckUserPercent(
                                        instalmentItem.instalmentNo - 1,
                                        "${preDecimal.value}.${postDecimal.value}".toDouble()
                                    )
                                    this.userPercent.text =
                                        "${preDecimal.value}.${postDecimal.value}%"
                                }
                            }
                            when {
                                index > -1 -> mListener.onQuantityChange(true)
                                else -> mListener.onQuantityChange(false)
                            }

                            this.userDueMoney.text = DecimalFormat("#,##,##,###.##")
                                .format(
                                    MainActivity.Companion.flatValue * getUserPercent(
                                        instalmentItem.instalmentNo - 1
                                    ) / 100
                                )
                        })
                    .setNegativeButton("CANCEL",
                        DialogInterface.OnClickListener { dialog, which ->
                            Log.e("Dialog", "Test Cancel")
                        })

                builder.show()
            }

            this.buffer.setOnClickListener { view ->
                val builder = AlertDialog.Builder(view.context)
                val dialog = inflate(
                    view.context,
                    R.layout.days_picker_dialog_box, null
                )
                val dialogText = dialog.findViewById<TextView>(R.id.dialog_text)
                dialogText.text = "Select Buffer Days for Instalment ${this.instalmentNo.text}"

                val daysPicker = dialog.findViewById<NumberPicker>(R.id.days_picker)
                daysPicker.minValue = 0
                daysPicker.maxValue = 365
                daysPicker.value = this.buffer.text.toString().toInt()

                builder.setView(dialog)
                    .setPositiveButton("DONE",
                        DialogInterface.OnClickListener { dialog1, which ->
                            if (daysPicker.value == 0) {
                                this.buffer.text = "-"
                            }
                            this.buffer.text = "${daysPicker.value}"
                            setBufferDays(instalmentItem.instalmentNo-1, daysPicker.value)
                            setPirmalDatesWithBuffer()
                        })
                    .setNegativeButton("CANCEL",
                        DialogInterface.OnClickListener { dialog, which ->
                            Log.e("Dialog", "Test Cancel")
                        })

                builder.show()
            }

        }
    }
}
