package com.example.myapplication

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myWebView: WebView = findViewById(R.id.webview)

        myWebView.settings.javaScriptEnabled = true
        myWebView.webViewClient = HealthConnectWebViewClient()
        myWebView.settings.domStorageEnabled = true
        myWebView.addJavascriptInterface(myWebView.webViewClient, "HealthConnect")
        myWebView.loadUrl("file:///android_asset/web.html")

//        val healthConnectManager by lazy {
//            HealthConnectManager(this)
//        }
//        setContent {
//            AppTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    WebViewScreen()
//                }
//            }
//        }
    }


}
/*

@Composable
fun WebViewScreen() {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                addJavascriptInterface(HealthConnectWebViewClient(context), "HealthConnect")
                loadUrl("file:///android_asset/web.html") // 로컬 HTML 파일 사용 권장
            }
        },
        update = { webView ->
            // WebView 업데이트 로직 (필요한 경우)
        }
    )
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme
    ) {
        content()
    }
}*/
