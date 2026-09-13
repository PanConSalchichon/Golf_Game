package com.example.mini_golf.physics

import com.example.mini_golf.model.Ball
import com.example.mini_golf.model.GolfHole

class GolfPhysicsEngineImpl : GolfPhysicsEngine {

    override fun calculateShot(input: SwingInput, ball: Ball, hole: GolfHole): ShotResult {
        val force = calculateForce(input.magnitude)
        return ShotResult(
            validStroke = true,
            force = force,
            directionRadians = 0f,
            newX = ball.x,
            newY = ball.y,
            holeCompleted = false
        )
    }
    private fun calculateForce(magnitude: Float): Float{
        val range = SwingConstants.MAX_SWING_MAGNITUDE - SwingConstants.SWING_THRESHOLD
        val rawForce = (magnitude - SwingConstants.SWING_THRESHOLD) / range
        return rawForce.coerceIn(0f, 1f)
    }
}