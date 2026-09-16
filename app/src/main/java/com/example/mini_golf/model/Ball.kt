package com.example.mini_golf.model

data class Ball(
    var x: Float = 0.0f,
    var y: Float = 0.0f,
    val initialX: Float = x,
    val initialY: Float = y
)