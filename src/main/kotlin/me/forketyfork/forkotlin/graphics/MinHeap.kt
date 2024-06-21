package me.forketyfork.forkotlin.graphics

class MinHeap(size: Int) {

    private var count = 0

    private val array: Array<Node?>

    init {
        // the actual size of the array should be the next power of 2
        var actualSize = 1
        while (actualSize < size + 1) {
            actualSize = actualSize shl 1
        }
        // initially, the array is empty
        array = Array(actualSize) {
            null
        }
    }

    fun add(node: Node) {
        if (count == array.size - 1) {
            error("Heap is full")
        }
        array[++count] = node
        node.heapPosition = count
        bubbleUp(count)
    }

    fun update(node: Node) {
        bubbleUp(node.heapPosition)
        bubbleDown(node.heapPosition)
    }

    fun poll(): Node {
        if (count == 0) {
            error("Heap is empty")
        }
        val result = array[1]!!
        array[1] = array[count]
        array[count] = null
        count--
        array[1]?.let {
            it.heapPosition = 1
            bubbleDown(1)
        }
        return result
    }

    fun isNotEmpty() = count > 0

    private fun Array<Node?>.dist(position: Int): Double = get(position)?.dist ?: Double.MAX_VALUE

    private fun bubbleDown(position: Int) {
        check(position in (1..count)) { "Position $position not in 1..$count" }
        val node = array[position]!!
        val leftPosition = position shl 1
        if (leftPosition < array.lastIndex) {
            val rightPosition = leftPosition + 1
            val minPosition =
                if (array.dist(leftPosition) < array.dist(rightPosition)) leftPosition else rightPosition
            if (node.dist > array.dist(leftPosition) || node.dist > array.dist(rightPosition)) {
                swap(node, array[minPosition]!!)
                bubbleDown(minPosition)
            }
        }
    }

    private fun bubbleUp(position: Int) {
        check(position in (1..count)) { "Position $position not in 1..$count" }
        val node = array[position]!!
        val nextPosition = position shr 1
        if (nextPosition > 0) {
            val nextNode = array[nextPosition]!!
            if (nextNode.dist > node.dist) {
                swap(node, nextNode)
                bubbleUp(nextPosition)
            }
        }
    }

    private fun swap(node1: Node, node2: Node) {
        array[node1.heapPosition] = node2
        array[node2.heapPosition] = node1
        val node1Position = node1.heapPosition
        node1.heapPosition = node2.heapPosition
        node2.heapPosition = node1Position
    }
}
