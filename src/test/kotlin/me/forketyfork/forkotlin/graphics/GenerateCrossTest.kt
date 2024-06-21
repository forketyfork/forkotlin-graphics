package me.forketyfork.forkotlin.graphics

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class GenerateCrossTest {

    data class TestCase(val width: Int, val height: Int, val expectedHash: String)

    companion object {
        @JvmStatic
        fun testCases(): List<TestCase> {
            return listOf(
                TestCase(20, 10, "b56a8b4fce6cfcc00965be5c9b1eb157"),
                TestCase(10, 10, "031a1b56b1a2754c69a6119c61b1f28f"),
                TestCase(20, 20, "a4b4885a3aa7a3acdc318885b865178b")
            )
        }
    }

    @ParameterizedTest
    @MethodSource("testCases")
    fun test(testCase: TestCase) {

        val image = generateCross(testCase.width, testCase.height)

        assertThat(image.height).isEqualTo(testCase.height)
        assertThat(image.width).isEqualTo(testCase.width)

        val hex = bytesToHex(md5Digest(image.toBMPBytes()))

        assertThat(hex).isEqualTo(testCase.expectedHash)
    }

}
