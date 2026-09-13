package com.example.mini_golf.physics

import android.gesture.GestureStroke
import com.example.mini_golf.model.Ball
import com.example.mini_golf.model.GolfHole

data class SwingInput (
    val magnitude: Float,
    val dx: Float,
    val dy: Float
)

data class ShotResult(
    val validStroke: Boolean,
    val force: Float,
    val directionRadians: Float,
    val newX: Float,
    val newY: Float,
    val holeCompleted: Boolean
)

interface GolfPhysicsEngine {
    fun calculateShot(input: SwingInput, ball: Ball, hole: GolfHole): ShotResult
}