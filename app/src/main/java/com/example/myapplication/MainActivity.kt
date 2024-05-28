import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.example.myapplication.R
import com.example.myapplication.RetrofitInstance
import com.example.myapplication.StepsData
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant

class MainActivity : AppCompatActivity() {

    private val healthConnectClient by lazy {
        HealthConnectClient.getOrCreate(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions()
    }

    private fun requestPermissions() {
        val permissions = setOf(
            "androidx.health.connect.permission.READ",
            "androidx.health.connect.permission.WRITE"
        )

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.all { it.value }) {
                // 권한이 허용됨
                fetchStepsAndSendToServer()
            } else {
                // 권한이 거부됨
                Toast.makeText(this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        requestPermissionLauncher.launch(permissions.toTypedArray())
    }

    private fun fetchStepsAndSendToServer() {
        CoroutineScope(Dispatchers.IO).launch {
            val endTime = Instant.now()
            val startTime = endTime.minusSeconds(7 * 24 * 60 * 60) // 1주일 전

            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    StepsRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )
            )

            val stepsDataList = response.records.map { record ->
                StepsData(record.count, record.endTime.toEpochMilli())
            }

            withContext(Dispatchers.Main) {
                sendDataToServer(stepsDataList)
            }
        }
    }

    private fun sendDataToServer(stepsDataList: List<StepsData>) {
        stepsDataList.forEach { stepsData ->
            RetrofitInstance.api.sendStepsData(stepsData).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Data sent successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Failed to send data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}