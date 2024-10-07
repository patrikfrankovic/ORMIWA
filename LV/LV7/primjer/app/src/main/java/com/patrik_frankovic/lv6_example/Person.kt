package com.patrik_frankovic.lv6_example

data class ResponseData(
    val status: String,
    val code: Number,
    val total: Number,
    val data: ArrayList<Persons>
)
data class Persons(
    val firstname: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val birthday: String,
    val gender: String,
    val address: Address,
    val website: String,
    val image: String
)
data class Address(
    val street: String,
    val streetName: String,
    val buildingNumber: Number,
    val city: String,
    val zipcode: String,
    val country: String,
    val county_code: String,
    val latitude: Double,
    val longitude: Double
)
