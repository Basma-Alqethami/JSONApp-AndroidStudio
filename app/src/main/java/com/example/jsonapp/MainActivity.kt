package com.example.jsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.AdapterView
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var butt: Button
    lateinit var textView_Result: TextView
    lateinit var spinner: Spinner
    lateinit var editText_input: EditText
    lateinit var textView_Date: TextView

    private var curencyDetails: Currencies? = null
    val curArray = arrayListOf("ada", "usd", "aud", "sar", "cny", "jpy")
    var result = 0.0
    var selected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        butt = findViewById(R.id.bttn)
        textView_Result = findViewById(R.id.result)
        spinner = findViewById(R.id.spinner)
        editText_input = findViewById(R.id.userinput)
        textView_Date = findViewById(R.id.date)

        setSpinner()

        butt.setOnClickListener {
            getValue()
        }
    }

    fun getValue () {
        var value = editText_input.text.toString()
        var currency: Double = value.toDouble()

        getCurrency(onResult = {
            curencyDetails = it

            when (selected) {
                0 -> calc(curencyDetails?.eur?.inr?.toDouble(), currency);
                1 -> calc(curencyDetails?.eur?.usd?.toDouble(), currency);
                2 -> calc(curencyDetails?.eur?.aud?.toDouble(), currency);
                3 -> calc(curencyDetails?.eur?.sar?.toDouble(), currency);
                4 -> calc(curencyDetails?.eur?.cny?.toDouble(), currency);
                5 -> calc(curencyDetails?.eur?.jpy?.toDouble(), currency);
            }
        })
    }

    fun calc(i: Double?, sel: Double) {
        var s = 0.0
        if (i != null) {
            s = (i * sel)
        }
        textView_Result.text = "result " + s
    }

    fun setSpinner() {

        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, curArray
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    selected = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action

                }
            }
        }
    }

    fun getCurrency(onResult: (Currencies?) -> Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if (apiInterface != null) {
            apiInterface.getCurrencies()?.enqueue(object : Callback<Currencies> {
                override fun onResponse(
                    call: Call<Currencies>,
                    response: Response<Currencies>
                ) {
                    textView_Date.text = response.body()!!.date
                    onResult(response.body())
                }

                override fun onFailure(call: Call<Currencies>, t: Throwable) {
                    onResult(null)
                    Toast.makeText(applicationContext, "" + t.message, Toast.LENGTH_SHORT)
                        .show();
                }

            })
        }
    }
}