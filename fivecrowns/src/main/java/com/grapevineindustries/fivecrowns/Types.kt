package com.grapevineindustries.fivecrowns

data class Player(
    var name: String,
    var score: Int = 0,
    var pendingPoints: Int = 0
)