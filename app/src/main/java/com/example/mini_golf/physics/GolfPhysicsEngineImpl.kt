package com.example.mini_golf.physics

import com.example.mini_golf.model.Ball
import com.example.mini_golf.model.GolfHole
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class PhysicsResult(
    val isValidSwing: Boolean,
    val force: Float,
    val angle: Float,
    val newBall: Ball,
    val isHoleCompleted: Boolean
)

class GolfPhysicsEngineImpl(
    private val minSwingThreshold: Float = 12.0f,
    private val maxForce: Float = 300.0f
) {

    fun calculateSwingForce(accelX: Float, accelY: Float, accelZ: Float): Float {
        val sumSquares = (accelX * accelX + accelY * accelY + accelZ * accelZ).toDouble()
        val totalAcceleration = sqrt(sumSquares).toFloat()

        return if (totalAcceleration >= minSwingThreshold) {
            val calculatedForce = totalAcceleration * 15.0f
            if (calculatedForce > maxForce) maxForce else calculatedForce
        } else {
            0.0f
        }
    }

    fun processSwing(
        accelX: Float,
        accelY: Float,
        accelZ: Float,
        currentBall: Ball,
        hole: GolfHole
    ): PhysicsResult {
        val force = calculateSwingForce(accelX, accelY, accelZ)

        if (force <= 0.0f) {
            return PhysicsResult(
                isValidSwing = false,
                force = 0.0f,
                angle = 0.0f,
                newBall = currentBall,
                isHoleCompleted = false
            )
        }

        val angle = atan2(accelY.toDouble(), accelX.toDouble()).toFloat()
        val deltaX = force * cos(angle.toDouble()).toFloat()
        val deltaY = force * sin(angle.toDouble()).toFloat()

        val newX = currentBall.x + deltaX
        val newY = currentBall.y + deltaY

        val dx = (hole.x - newX).toDouble()
        val dy = (hole.y - newY).toDouble()
        val distanceToHole = sqrt(dx * dx + dy * dy).toFloat()

        val isCompleted = distanceToHole <= hole.radius
        val updatedBall = currentBall.copy(x = newX, y = newY)

        return PhysicsResult(
            isValidSwing = true,
            force = force,
            angle = angle,
            newBall = updatedBall,
            isHoleCompleted = isCompleted
        )
    }
}
