package com.grapevineindustries.fivecrowns.data

data class Player(
    var name: String,
    var score: Int = 0,
    var pendingPoints: Int = 0
)