package com.example.health.health

import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.example.health.api.RetrofitInstance
import com.example.health.api.StepsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant

class HealthClient(private val context: Context, private val healthConnectClient: HealthConnectClient, private val webView: WebView/*, private val requestPermissions: ActivityResultLauncher<Set<String>>*/): WebViewClient() {
    suspend fun getRecords() {
        val endTime = Instant.now()
        val startTime = endTime.minusSeconds(30 * 24 * 60 * 60) // 1주일 전
        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )
        println("a")
        response.records.forEach {
            val steps = it.count
            println("AAAAAA" + steps);

            webView.post {
                webView.evaluateJavascript("javascript:showSteps($steps)", null)
            }
        }

        val stepsDataList = response.records.map { record ->
            StepsData(record.count, record.endTime.toEpochMilli())
        }

        withContext(Dispatchers.Main) {
            RetrofitInstance.sendDataToServer(stepsDataList, context)
        }
    }
    suspend fun insertSteps() {
        val endTime = Instant.now()
        val startTime = endTime.minusSeconds(1 * 60 * 60)
        try {
            val stepsRecord = StepsRecord(
                count = 120,
                startTime = startTime,
                endTime = endTime,
                startZoneOffset = null,
                endZoneOffset = null,
            )
            healthConnectClient.insertRecords(listOf(stepsRecord))
        } catch (e: Exception) {
            // Run error handling here
        }
    }

    /*suspend fun checkPermissionsAndRun() {
        val PERMISSIONS =
            setOf(
                HealthPermission.getReadPermission(HeartRateRecord::class),
                HealthPermission.getWritePermission(HeartRateRecord::class),
                HealthPermission.getReadPermission(StepsRecord::class),
                HealthPermission.getWritePermission(StepsRecord::class)
            )

        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        if (granted.containsAll(PERMISSIONS)) {
            println("OOOOOO")
            requestPermissions.launch(PERMISSIONS)
            // Permissions already granted; proceed with inserting or reading data
        } else {
            requestPermissions.launch(PERMISSIONS)
        }
    }*/
}