package me.forketyfork.forkotlin.graphics

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import javax.imageio.ImageIO

class NegativeTest {

    data class TestCase(val fileName: String, val expectedHash: String)

    companion object {
        @JvmStatic
        fun testCases(): List<TestCase> {
            return listOf(
                TestCase("/small.png", "b25b6f88aaa616e81c04cf3bc2713946"),
                TestCase("/blue.png", "f2f4c0ea34926b1a711a6d04bd108923"),
                TestCase("/trees.png", "e6eaf77401b4d6d9c27368bdb11a0862")
            )
        }
    }

    @ParameterizedTest
    @MethodSource("testCases")
    fun test(testCase: TestCase) {

        val image = ImageIO.read(javaClass.getResource(testCase.fileName))
        image.negate()

        val hex = bytesToHex(md5Digest(image.toBMPBytes()))

        assertThat(hex).isEqualTo(testCase.expectedHash)
    }

}
