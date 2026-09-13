package com.example.mini_golf.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.example.mini_golf.physics.SwingInput
import kotlin.math.sqrt

class SensorController(context: Context) : SensorEventListener {
    companion object {
        private const val SWING_THRESHOLD = 18f
        private const val COOLDOWN_MS = 800L
    }

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var lastSwingTime = 0L
    private var swingListener: ((SwingInput) -> Unit)? = null

    fun setOnSwingDetectedListener(listener: (SwingInput) -> Unit) {
        swingListener = listener
    }

    fun start() {
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val magnitude = sqrt(x * x + y * y + z * z)
        val now = System.currentTimeMillis()

        val isStrongEnough = magnitude > SWING_THRESHOLD
        val cooldownPassed = now - lastSwingTime > COOLDOWN_MS

        if (isStrongEnough && cooldownPassed) {
            lastSwingTime = now
            val input = SwingInput(magnitude = magnitude, dx = x, dy = y)
            swingListener?.invoke(input)
            Log.d("SensorController", "Swing detected: magnitude=$magnitude dx=$x dy=$y")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}