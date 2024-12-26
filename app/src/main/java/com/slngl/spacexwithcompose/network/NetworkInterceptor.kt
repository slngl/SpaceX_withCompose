package com.slngl.spacexwithcompose.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        try {
            chain.proceed(chain.request())
        } catch (e: IOException) {
            throw IOException("Network error: ${e.localizedMessage}")
        }
}
