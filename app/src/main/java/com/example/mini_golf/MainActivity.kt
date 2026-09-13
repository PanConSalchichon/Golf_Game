package com.example.mini_golf

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mini_golf.model.Ball
import com.example.mini_golf.model.GolfHole
import com.example.mini_golf.physics.GolfPhysicsEngineImpl
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

        val physicsEngine = GolfPhysicsEngineImpl()
        val testBall = Ball(initialX = 0f, initialY = 0f)
        val testHole = GolfHole(holeNumber = 1, par = 3, x = 100f, y = 100f)

        sensorController.setOnSwingDetectedListener { swingInput ->
            val result = physicsEngine.calculateShot(swingInput, testBall, testHole)
            Log.d("MainActivity", "newX=${result.newX} newY=${result.newY} holeCompleted=${result.holeCompleted}")
            testBall.x = result.newX
            testBall.y = result.newY
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