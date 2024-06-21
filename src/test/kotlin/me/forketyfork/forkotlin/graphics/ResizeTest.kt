package me.forketyfork.forkotlin.graphics

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import javax.imageio.ImageIO

class ResizeTest {

    data class TestCase(
        val fileName: String,
        val width: Int,
        val height: Int,
        val expectedWidth: Int,
        val expectedHeight: Int,
        val expectedHash: String
    )

    companion object {
        @JvmStatic
        fun testCases(): List<TestCase> {
            return listOf(
                TestCase("/small.png", 1, 1, 14, 9, "05003a57141113859888cdc868558a4a"),
                TestCase("/blue.png", 125, 50, 375, 284, "40fb8613a81d715cfa115e51eff16f50"),
                TestCase("/trees.png", 100, 30, 500, 399, "3a654d1f414fba69d55f7359ea9ca25c")
            )
        }
    }

    @ParameterizedTest
    @MethodSource("testCases")
    fun test(testCase: TestCase) {

        val image = ImageIO.read(javaClass.getResource(testCase.fileName))

        val resizedImage = resizeImage(image, testCase.width, testCase.height)
        assertThat(resizedImage.width).isEqualTo(testCase.expectedWidth)
        assertThat(resizedImage.height).isEqualTo(testCase.expectedHeight)

        val actualHex = bytesToHex(md5Digest(resizedImage.toBMPBytes()))
        assertThat(actualHex).isEqualTo(testCase.expectedHash)
    }

}
