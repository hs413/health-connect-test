package com.example.health

import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

class HealthClient(private val healthConnectClient: HealthConnectClient, private val webView: WebView): WebViewClient() {
    suspend fun getRecords() {
        val endTime = Instant.now()
        val startTime = endTime.minusSeconds(7 * 24 * 60 * 60) // 1주일 전
        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )

        response.records.forEach {
            val steps = it.count
            println("AAAAAA" + steps);

            webView.post {
                webView.evaluateJavascript("javascript:showSteps($steps)", null)
            }
        }



    }
}