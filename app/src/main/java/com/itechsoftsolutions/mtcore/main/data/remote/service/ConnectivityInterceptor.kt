package com.itechsoftsolutions.mtcore.main.data.remote.service

import com.itechsoftsolutions.mtcore.utils.helper.network.NetworkUtils
import com.itechsoftsolutions.mtcore.utils.helper.network.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * This is the interceptor class of the project. This class intercepts with the API client.
 * @author Mohd. Asfaq-E-Azam Rifat
 */
class ConnectivityInterceptor : Interceptor {

    /**
     * This method intercepts with the API client. If it's not online then
     * throws a [NoConnectivityException]
     * @param chain interceptor chain
     * @return response
     * */
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtils.isOnline()) {
            throw NoConnectivityException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}