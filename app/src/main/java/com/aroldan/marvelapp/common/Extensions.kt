package com.aroldan.marvelapp.common

fun ByteArray.toHex() =
    joinToString("") { "%02x".format(it) }
