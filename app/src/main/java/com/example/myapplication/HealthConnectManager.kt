package com.example.myapplication

import android.content.Context
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.HealthConnectClient


const val MIN_SUPPORTED_SDK = Build.VERSION_CODES.O_MR1

class HealthConnectManager(private val context: Context) {
    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }

    var availability = mutableStateOf(HealthConnectAvailability.NOT_SUPPORTED)
        private set
    init {
        checkAvailability()
    }

    fun checkAvailability() {
        availability.value = when {
            HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE -> HealthConnectAvailability.INSTALLED
            isSupported() -> HealthConnectAvailability.NOT_INSTALLED
            else -> HealthConnectAvailability.NOT_SUPPORTED
        }
    }

    private fun isSupported() = Build.VERSION.SDK_INT >= MIN_SUPPORTED_SDK
}

enum class HealthConnectAvailability {
    INSTALLED,
    NOT_INSTALLED,
    NOT_SUPPORTED
}