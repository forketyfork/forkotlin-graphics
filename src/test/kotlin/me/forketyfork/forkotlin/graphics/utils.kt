package me.forketyfork.forkotlin.graphics

import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.security.MessageDigest
import javax.imageio.ImageIO

fun BufferedImage.toBMPBytes(): ByteArray =
    ByteArrayOutputStream().also {
        ImageIO.write(this, "bmp", it)

    }.toByteArray()

fun md5Digest(bytes: ByteArray): ByteArray =
    MessageDigest.getInstance("MD5").also { it.update(bytes) }.digest()

// TODO also used in git-internals
fun bytesToHex(bytes: ByteArray): String =
    BigInteger(1, bytes).toString(16).padStart(32, '0')
