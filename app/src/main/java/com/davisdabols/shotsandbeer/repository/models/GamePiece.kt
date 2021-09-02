package com.davisdabols.shotsandbeer.repository.models

data class GamePiece(
    val ID: Int,
    val value: Int,
    var isFound: Boolean = false,
    var wrongLocation: Boolean = false
) {
    val valueString = value.toString()
}
