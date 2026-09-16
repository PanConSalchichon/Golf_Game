package com.example.mini_golf.model

data class GolfHole(
    val x: Float = 100.0f,
    val y: Float = 100.0f,
    val radius: Float = 20.0f,
    val holeNumber: Int,
    val par: Int
)