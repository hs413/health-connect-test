package com.example.health.health

import android.webkit.JavascriptInterface
import android.webkit.WebViewClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HealthWebViewClient(private val healthClient: HealthClient): WebViewClient() {

    @JavascriptInterface
    fun getDataRecords() {
        println("read")
        CoroutineScope(Dispatchers.IO).launch {
            healthClient.getRecords()
        }
    }

    /*@JavascriptInterface
    fun checkPermission() {
        println("permit")
        CoroutineScope(Dispatchers.IO).launch {
            healthClient.checkPermissionsAndRun()
        }
    }*/

    @JavascriptInterface
    fun insert() {
        println("insert")
        CoroutineScope(Dispatchers.IO).launch {
            healthClient.insertSteps()
        }
    }
}