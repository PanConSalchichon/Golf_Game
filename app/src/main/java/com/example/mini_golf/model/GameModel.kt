package com.example.mini_golf.model

// Si tienes un enum GameState en este mismo paquete o un archivo propio

data class GameModel(
    val ball: Ball = Ball(),
    val hole: GolfHole = GolfHole(holeNumber = 1, par = 3),
    val strokes: Int = 0,
    val state: GameState = GameState.WAITING_FOR_SWING
)