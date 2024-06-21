package me.forketyfork.forkotlin.graphics

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.sqrt

fun BufferedImage.shift(row: Int, col: Int, transposed: Boolean) {
    if (transposed) {
        for (idx in (col until height - 1)) {
            setRGB(row, idx, getRGB(row, idx + 1))
        }
    } else {
        for (idx in (col until width - 1)) {
            setRGB(idx, row, getRGB(idx + 1, row))
        }
    }
}

fun BufferedImage.intensities(transposed: Boolean, actualWidth: Int, actualHeight: Int): Array<DoubleArray> {
    val intensities = if (transposed) {
        Array(actualWidth) { y ->
            DoubleArray(actualHeight) { x ->
                calculateIntensity(y, x, actualWidth, actualHeight)
            }
        }
    } else {
        Array(actualHeight) { y ->
            DoubleArray(actualWidth) { x ->
                calculateIntensity(x, y, actualWidth, actualHeight)
            }
        }
    }

    val maxIntensity = intensities.maxOf { it.maxOrNull()!! }

    intensities.forEach { row ->
        for (col in (0..row.lastIndex)) {
            row[col] = 255.0 * row[col] / maxIntensity
        }
    }

    return intensities
}

private fun BufferedImage.calculateIntensity(x: Int, y: Int, actualWidth: Int, actualHeight: Int): Double =
    sqrt(gradX(x, y, actualWidth) + gradY(x, y, actualHeight))

private fun BufferedImage.gradX(x: Int, y: Int, actualWidth: Int): Double {
    val xAdj = if (x == 0) 1 else if (x == actualWidth - 1) actualWidth - 2 else x
    val left = Color(getRGB(xAdj - 1, y))
    val right = Color(getRGB(xAdj + 1, y))
    return grad(left, right)
}

private fun BufferedImage.gradY(x: Int, y: Int, actualHeight: Int): Double {
    val yAdj = if (y == 0) 1 else if (y == actualHeight - 1) actualHeight - 2 else y
    val top = Color(getRGB(x, yAdj - 1))
    val bottom = Color(getRGB(x, yAdj + 1))
    return grad(top, bottom)
}

private fun grad(p1: Color, p2: Color): Double {
    val rx = (p1.red - p2.red).toDouble()
    val gx = (p1.green - p2.green).toDouble()
    val bx = (p1.blue - p2.blue).toDouble()
    return rx * rx + gx * gx + bx * bx
}
