package com.patrik_frankovic.lv6_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.personList)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PersonRecyclerAdapter(ArrayList())
        }
        val service = ServiceBuilder.buildService(FakerEndpoints::class.java)
        val request = service.getPersons(10)

        request.enqueue(
            object : Callback<ResponseData>{
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    if (response.isSuccessful)
                        recyclerView.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = PersonRecyclerAdapter(response.body()!!.data)
                        }
                }
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.e("MainActivity", t.message.toString())
                }

            }

        )

    }
}