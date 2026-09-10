package com.example.mini_golf.model

data class Ball (
    val initialX: Float,
    val initialY: Float,
    var x: Float = initialX,
    var y: Float = initialY
)