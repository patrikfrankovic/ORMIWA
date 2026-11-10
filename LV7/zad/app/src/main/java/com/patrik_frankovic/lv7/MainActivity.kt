package com.patrik_frankovic.lv7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val inputText = findViewById<EditText>(R.id.inputText)
            button.setOnClickListener {
                val text = inputText.text.toString()
                val request =
                    ServiceBuilder.buildService(MakeUpEndpoints::class.java)
                val call = request.getMakeUp(text)
                call.enqueue(object : Callback<ArrayList<ResponseData>> {
                    override fun onResponse(
                        call: Call<ArrayList<ResponseData>>, response:
                        Response<ArrayList<ResponseData>>
                    ) {
                        if (response.isSuccessful) {
                            findViewById<RecyclerView>(R.id.makeUpList).apply {
                                layoutManager =
                                    LinearLayoutManager(this@MainActivity)
                                adapter =
                                    MakeUpRecyclerAdapter(response.body()!!)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<ResponseData>>, t: Throwable) {
                        Log.d("FAIL", t.message.toString())
                    }
                })
            }
        }
    }



