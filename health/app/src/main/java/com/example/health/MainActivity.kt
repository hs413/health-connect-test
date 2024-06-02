package com.example.health

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import com.example.health.health.HealthClient
import com.example.health.health.HealthConnectManager
import com.example.health.health.HealthWebViewClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val healthConnectManager = HealthConnectManager(applicationContext)

        if (!healthConnectManager.getSdkStatus()) return
        /*val PERMISSIONS =
            setOf(
                HealthPermission.getReadPermission(HeartRateRecord::class),
                HealthPermission.getWritePermission(HeartRateRecord::class),
                HealthPermission.getReadPermission(StepsRecord::class),
                HealthPermission.getWritePermission(StepsRecord::class)
            )
        val requestPermissionActivityContract = PermissionController.createRequestPermissionResultContract()

        val requestPermissions = registerForActivityResult(requestPermissionActivityContract) { granted ->
            if (granted.containsAll(PERMISSIONS)) {
                println("permitted")
                // Permissions successfully granted
            } else {
                println("NOOOOOOO")
                // Lack of required permissions
            }
        }*/
        setContentView(R.layout.activity_main)
        val myWebView: WebView = findViewById(R.id.webview)
        val connectClient = healthConnectManager.getConnectClient()
        val healthClient = HealthClient(applicationContext, connectClient, myWebView/*, requestPermissions*/)

        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.domStorageEnabled = true
        myWebView.webViewClient = HealthWebViewClient(healthClient)
        myWebView.addJavascriptInterface(myWebView.webViewClient, "HealthConnect")
        myWebView.loadUrl("file:///android_asset/web.html")


//        enableEdgeToEdge()
//        setContent {
//            HealthTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    WebViewScreen(client)
//                }
//            }
//        }
    }
}

//@Composable
//fun WebViewScreen(client: HealthClient) {
//    AndroidView(
//        factory = { context ->
//            WebView(context).apply {
//                settings.javaScriptEnabled = true;
//                settings.domStorageEnabled = true
//                addJavascriptInterface(client, "HealthConnect")
//                loadUrl("file:///android_asset/web.html") // 로컬 HTML 파일 사용 권장
//            }
//        },
//        update = { webView ->
//            // WebView 업데이트 로직 (필요한 경우)
//        }
//    )
//}
//
//@Composable
//fun AppTheme(content: @Composable () -> Unit) {
//    MaterialTheme(
//        colorScheme = MaterialTheme.colorScheme
//    ) {
//        content()
//    }
//}