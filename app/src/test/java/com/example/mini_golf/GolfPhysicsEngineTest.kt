package com.example.mini_golf.physics

import com.example.mini_golf.model.Ball
import com.example.mini_golf.model.GolfHole
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test
import kotlin.math.atan2

class GolfPhysicsEngineTest {

    private val engine = GolfPhysicsEngineImpl()

    @Test
    fun `force is zero when magnitude equals threshold`() {
        val input = SwingInput(magnitude = SwingConstants.SWING_THRESHOLD, dx = 1f, dy = 0f)
        val ball = Ball(initialX = 0f, initialY = 0f)
        val hole = GolfHole(holeNumber = 1, par = 3, x = 500f, y = 500f)

        val result = engine.calculateShot(input, ball, hole)

        assertEquals(0f, result.force, 0.01f)
    }

    @Test
    fun `force is capped at one for magnitude above max`() {
        val input = SwingInput(magnitude = SwingConstants.MAX_SWING_MAGNITUDE + 50f, dx = 1f, dy = 0f)
        val ball = Ball(initialX = 0f, initialY = 0f)
        val hole = GolfHole(holeNumber = 1, par = 3, x = 500f, y = 500f)

        val result = engine.calculateShot(input, ball, hole)

        assertEquals(1f, result.force, 0.01f)
    }

    @Test
    fun `direction matches expected angle for given dx dy`() {
        val dx = 5f
        val dy = 5f
        val expectedAngle = atan2(dy, dx)
        val input = SwingInput(magnitude = 60f, dx = dx, dy = dy)
        val ball = Ball(initialX = 0f, initialY = 0f)
        val hole = GolfHole(holeNumber = 1, par = 3, x = 500f, y = 500f)

        val result = engine.calculateShot(input, ball, hole)

        assertEquals(expectedAngle, result.directionRadians, 0.01f)
    }

    @Test
    fun `hole is completed when ball lands within radius`() {
        // Ball starts right next to the hole, any real swing should reach it
        val hole = GolfHole(holeNumber = 1, par = 3, x = 100f, y = 100f, radius = 40f)
        val ball = Ball(initialX = 90f, initialY = 90f)
        // Very weak swing so the ball barely moves, staying inside the radius
        val input = SwingInput(magnitude = SwingConstants.SWING_THRESHOLD + 1f, dx = 1f, dy = 0f)

        val result = engine.calculateShot(input, ball, hole)

        assertTrue(result.holeCompleted)
    }

    @Test
    fun `hole is not completed when ball lands outside radius`() {
        val hole = GolfHole(holeNumber = 1, par = 3, x = 100f, y = 100f, radius = 40f)
        val ball = Ball(initialX = 0f, initialY = 0f)
        // Strong swing, ball should travel far past the hole's radius
        val input = SwingInput(magnitude = SwingConstants.MAX_SWING_MAGNITUDE, dx = 1f, dy = 0f)

        val result = engine.calculateShot(input, ball, hole)

        assertFalse(result.holeCompleted)
    }
}