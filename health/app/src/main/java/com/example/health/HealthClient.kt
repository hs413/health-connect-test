package com.example.health

import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

class HealthClient(private val healthConnectClient: HealthConnectClient, private val webView: WebView/*, private val requestPermissions: ActivityResultLauncher<Set<String>>*/): WebViewClient() {
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