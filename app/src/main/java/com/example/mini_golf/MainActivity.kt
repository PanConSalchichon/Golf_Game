package com.example.mini_golf

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mini_golf.sensor.SensorController

class MainActivity : AppCompatActivity() {
    private lateinit var sensorController: SensorController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sensorController = SensorController(this)

        sensorController.setOnSwingDetectedListener { swingInput ->
            Log.d("MainActivity", "Received swing: $swingInput")
        }
    }
    override fun onResume(){
        super.onResume()
        sensorController.start()
    }
    override fun onPause(){
        super.onPause()
        sensorController.stop()
    }
}