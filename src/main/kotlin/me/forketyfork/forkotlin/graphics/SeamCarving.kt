package me.forketyfork.forkotlin.graphics

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val arguments = try {
        readArgs(args)
    } catch (e: Exception) {
        println("Wrong arguments")
        exitProcess(-1)
    }

    val image = ImageIO.read(File(arguments.inFile))

    val result = when (arguments.op) {
        Op.NEGATE -> {
            image.negate()
            image
        }

        Op.GENERATE -> {
            generateCross(arguments.width, arguments.height)
        }

        Op.RESIZE -> {
            resizeImage(image, arguments.width, arguments.height)
        }

        Op.INTENSITY -> {
            image.intensityImage()
            image
        }
    }

    ImageIO.write(result, "png", File(arguments.outFile))
}

fun BufferedImage.negate() {
    for (x in (0 until width)) {
        for (y in (0 until height)) {
            val color = Color(getRGB(x, y))
            setRGB(x, y, Color(255 - color.red, 255 - color.green, 255 - color.blue).rgb)
        }
    }
}

fun BufferedImage.intensityImage() {
    val intensities = intensities(false, width, height)
    for (y in (0 until height)) {
        for (x in (0 until width)) {
            val grayscale = intensities[y][x].toInt()
            setRGB(x, y, Color(grayscale, grayscale, grayscale).rgb)
        }
    }
}

fun generateCross(width: Int, height: Int): BufferedImage {
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    with(image.createGraphics()) {
        color = Color.RED
        drawLine(0, 0, width - 1, height - 1)
        drawLine(width - 1, 0, 0, height - 1)
    }
    return image
}

fun readArgs(args: Array<String>): Arguments {
    val values = listOf("in", "out", "op", "width", "height")
        .map { "-$it" }
        .map { args.indexOf(it) + 1 }
        .map { args[it] }
    return Arguments(
        values[0],
        values[1],
        Op.valueOf(values[2].uppercase()),
        values[3].toInt(),
        values[4].toInt()
    )
}

fun resizeImage(image: BufferedImage, width: Int, height: Int): BufferedImage {
    var actualWidth = image.width
    var actualHeight = image.height

    repeat(height) {
        val transposed = true
        findSeam(image.intensities(transposed, actualWidth, actualHeight))
            .forEachIndexed { idx, col -> image.shift(idx, col, transposed) }
        actualHeight--
    }

    repeat(width) {
        val transposed = false
        findSeam(image.intensities(transposed, actualWidth, actualHeight))
            .forEachIndexed { idx, col -> image.shift(idx, col, transposed) }
        actualWidth--
    }

    val outImage = BufferedImage(image.width - width, image.height - height, image.type)
    for (row in 0 until outImage.height) {
        for (col in 0 until outImage.width) {
            outImage.setRGB(col, row, image.getRGB(col, row))
        }
    }
    return outImage

}

enum class Op {
    NEGATE,
    GENERATE,
    RESIZE,
    INTENSITY
}

data class Arguments(val inFile: String, val outFile: String, val op: Op, val width: Int, val height: Int)
