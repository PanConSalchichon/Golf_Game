package com.example.mini_golf

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mini_golf.model.Ball
import com.example.mini_golf.model.GolfHole
import com.example.mini_golf.physics.GolfPhysicsEngineImpl
import com.example.mini_golf.sensor.SensorController

class MainActivity : AppCompatActivity() {

    private lateinit var sensorController: SensorController
    private val physicsEngine = GolfPhysicsEngineImpl()

    private var testBall = Ball(x = 0.0f, y = 0.0f)
    private var testHole = GolfHole(x = 100.0f, y = 100.0f, radius = 20.0f, holeNumber = 1, par = 3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorController = SensorController(context = this)

        sensorController.setOnSwingDetectedListener { accelX, accelY, accelZ ->
            val result = physicsEngine.processSwing(accelX, accelY, accelZ, testBall, testHole)

            if (result.isValidSwing) {
                testBall = result.newBall
                Log.d("MainActivity", "Nueva posición de la bola: (${testBall.x}, ${testBall.y})")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorController.startListening()
    }

    override fun onPause() {
        super.onPause()
        sensorController.stopListening()
    }
}