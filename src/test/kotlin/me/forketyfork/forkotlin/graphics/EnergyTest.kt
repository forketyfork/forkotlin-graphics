package me.forketyfork.forkotlin.graphics

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import javax.imageio.ImageIO

class EnergyTest {

    data class TestCase(val fileName: String, val hash: String)

    companion object {
        @JvmStatic
        fun testCases(): List<TestCase> {
            return listOf(
                TestCase("/small.png", "931d2f37bb499ef6892db026f57525ba"),
                TestCase("/blue.png", "0bdde2d55124785b16df005088f17e1a"),
                TestCase("/trees.png", "89c4037e6c0b0de040d9fb85e4450ebc")
            )
        }
    }

    @ParameterizedTest
    @MethodSource("testCases")
    fun test(testCase: TestCase) {

        val image = ImageIO.read(javaClass.getResource(testCase.fileName))
        val width = image.width
        val height = image.height
        image.intensityImage()

        assertThat(image.height).isEqualTo(height)
        assertThat(image.width).isEqualTo(width)

        val hex = bytesToHex(md5Digest(image.toBMPBytes()))

        assertThat(hex).isEqualTo(testCase.hash)
    }

}
