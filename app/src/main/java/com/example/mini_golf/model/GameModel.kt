package com.example.mini_golf.model

data class GameModel (
    val hole: GolfHole,
    val ball: Ball,
    var strokes: Int = 0,
    var state: GameState = GameState.WAITING_FOR_SWING
)