package com.example.myapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class StepsData(
    val count: Long,
    val timestamp: Long
)

interface ApiService {
    @POST("path/to/your/endpoint")
    fun sendStepsData(@Body stepsData: StepsData): Call<Void>
}