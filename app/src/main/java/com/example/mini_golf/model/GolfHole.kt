package com.example.mini_golf.model

data class GolfHole (
    val holeNumber: Int,
    val par: Int,
    val x: Float,
    val y: Float,
    val radius: Float = 40f
)