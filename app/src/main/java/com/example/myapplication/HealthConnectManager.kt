package com.example.myapplication

import android.content.Context
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant

class HealthConnectManager(private val context: Context) {
    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }

    fun healthConnect() {
        val endTime = Instant.now()
        val startTime = endTime.minusSeconds(7 * 24 * 60 * 60)
        CoroutineScope(Dispatchers.IO).launch {
            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    StepsRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime))
            )

            withContext(Dispatchers.Main) {
                response.records.forEach {
                    val steps = it.count
                    // JavaScript 함수를 호출하여 웹 뷰에 데이터 전달
//                    webView.evaluateJavascript("javascript:showSteps($steps)", null)
                }
            }
        }
//        healthConnectClient.readRecords(
//            ReadRecordsRequest(StepsRecord::class)
//        )
    }
}