package com.aroldan.marvelapp.common.data

import com.aroldan.marvelapp.common.toHex
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.text.Charsets.UTF_8

class MarvelIncerceptor (private val key: Pair<String, String>): Interceptor {

    private val TIMESTAMP = "ts"
    private val APIKEY = "apikey"
    private val HASH = "hash"

    override fun intercept(chain: Interceptor.Chain): Response {
        val publicKey = key.first
        val privateKey = key.second

        val timestamp = System.currentTimeMillis().toString()

        val hash = generateHash("$timestamp$privateKey$publicKey")

        val url = chain.request().url().newBuilder()
            .addQueryParameter(TIMESTAMP, timestamp)
            .addQueryParameter(APIKEY, publicKey)
            .addQueryParameter(HASH, hash)
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        //Timber.i("Url: $url")

        return chain.proceed(request)
    }

    private fun generateHash(data: String): String? =
        try {
            MessageDigest.getInstance("MD5").digest(data.toByteArray(UTF_8)).toHex()
        } catch (exception: NoSuchAlgorithmException) {
            null
        }

}