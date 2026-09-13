package com.example.mini_golf.physics

import com.example.mini_golf.model.Ball
import com.example.mini_golf.model.GolfHole
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class GolfPhysicsEngineImpl : GolfPhysicsEngine {

    override fun calculateShot(input: SwingInput, ball: Ball, hole: GolfHole): ShotResult {
        val force = calculateForce(input.magnitude)
        val direction = calculateDirection(input.dx, input.dy)
        val distance = force * SwingConstants.MAX_SHOT_DISTANCE
        val newX = ball.x + cos(direction) * distance
        val newY = ball.y + sin(direction) * distance

        val holeCompleted = isBallInHole(newX, newY, hole)

        return ShotResult(
            validStroke = true,
            force = force,
            directionRadians = direction,
            newX = newX,
            newY = newY,
            holeCompleted = holeCompleted
        )
    }

    private fun calculateForce(magnitude: Float): Float {
        val range = SwingConstants.MAX_SWING_MAGNITUDE - SwingConstants.SWING_THRESHOLD
        val rawForce = (magnitude - SwingConstants.SWING_THRESHOLD) / range
        return rawForce.coerceIn(0f, 1f)
    }

    private fun calculateDirection(dx: Float, dy: Float): Float {
        return atan2(dy, dx)
    }

    private fun isBallInHole(ballX: Float, ballY: Float, hole: GolfHole): Boolean {
        val dx = ballX - hole.x
        val dy = ballY - hole.y
        val distance = sqrt(dx * dx + dy * dy)
        return distance <= hole.radius
    }
}