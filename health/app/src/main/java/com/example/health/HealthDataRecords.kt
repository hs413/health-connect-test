package com.example.health

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

class HealthDataRecords(private val healthConnectClient: HealthConnectClient) {
    suspend fun getRecords() {
        CoroutineScope(Dispatchers.IO).launch {
            val endTime = Instant.now()
            val startTime = endTime.minusSeconds(7 * 24 * 60 * 60) // 1주일 전
            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    StepsRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )
            )
        }
    }
}