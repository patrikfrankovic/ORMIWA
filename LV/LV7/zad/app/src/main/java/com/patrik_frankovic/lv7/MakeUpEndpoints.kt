package com.patrik_frankovic.lv7

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MakeUpEndpoints {
    @GET("/api/v1/products.json")
    fun getMakeUp(@Query("brand") brand: String): Call<ArrayList<ResponseData>>
}

