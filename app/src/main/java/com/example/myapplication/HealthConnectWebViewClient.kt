package com.example.myapplication

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebViewClient
import androidx.health.connect.client.HealthConnectClient

class HealthConnectWebViewClient() : WebViewClient() {
    @JavascriptInterface
    fun getHealthData(dataType: String) {
        print("AAAAAAAAAAAAAAAAAAA")
        print(dataType)
        // Health Connect 권한 확인 및 데이터 요청 로직
        // 결과를 JavaScript로 반환
    }
}