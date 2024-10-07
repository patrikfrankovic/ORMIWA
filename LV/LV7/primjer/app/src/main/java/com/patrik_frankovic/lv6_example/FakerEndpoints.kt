package com.patrik_frankovic.lv6_example

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FakerEndpoints {
    @GET("https://fakerapi.it/api/v1/persons")
    fun getPersons(@Query("_quantity") count: Int): Call<ResponseData>
}