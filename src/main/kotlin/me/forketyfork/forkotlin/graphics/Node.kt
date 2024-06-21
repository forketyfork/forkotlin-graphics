package me.forketyfork.forkotlin.graphics

class Node(
    val row: Int,
    val col: Int,
    val intensity: Double,
    var dist: Double,
    var parent: Node? = null,
    var processed: Boolean = false,
    var heapPosition: Int = 0
)
