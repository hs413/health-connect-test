package com.example.health

import android.webkit.JavascriptInterface
import android.webkit.WebViewClient

class HealthWebViewClient(private val healthClient: HealthClient): WebViewClient() {

    @JavascriptInterface
    suspend fun getDataRecords() {
        healthClient.getRecords()
    }

}