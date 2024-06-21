package me.forketyfork.forkotlin.graphics

fun findSeam(intensities: Array<DoubleArray>): IntArray {

    val height = intensities.size + 2
    val width = intensities[0].size
    val heap = MinHeap(height * width)
    val nodeArray = Array(height) { row ->
        Array(width) { col ->
            Node(
                row, col,
                if (row == 0 || row == height - 1) 0.0 else intensities[row - 1][col],
                if (row == 0 && col == 0) 0.0 else Double.MAX_VALUE
            ).also(heap::add)
        }
    }
    while (heap.isNotEmpty()) {
        val u = heap.poll()
        buildList {
            when (u.row) {
                0 -> {
                    add(0 to u.col + 1)
                    add(1 to u.col)
                }

                height - 1 -> {
                    add(height - 1 to u.col + 1)
                }

                else -> {
                    add(u.row + 1 to u.col - 1)
                    add(u.row + 1 to u.col)
                    add(u.row + 1 to u.col + 1)
                }
            }
        }.filter { it.first in (0 until height) && it.second in (0 until width) }
            .map { (row, col) -> nodeArray[row][col] }
            .filter { !it.processed }
            .forEach { v ->
                val dist = u.dist + v.intensity
                if (dist < v.dist) {
                    v.parent = u
                    v.dist = dist
                    heap.update(v)
                }
            }
        u.processed = true
    }

    val result = IntArray(height - 2)
    var idx = height - 3
    var parent: Node? = nodeArray[height - 1][width - 1]
    while (parent != null) {
        if (parent.row > 0 && parent.row < height - 1) {
            result[idx--] = parent.col
        }
        parent = parent.parent
    }

    return result
}
