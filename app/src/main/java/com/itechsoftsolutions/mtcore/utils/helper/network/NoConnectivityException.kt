package com.itechsoftsolutions.mtcore.utils.helper.network

import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import java.io.IOException

/**
 * This is an exception class for no connectivity of internet
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
class NoConnectivityException : IOException() {
    override val message: String
        get() = DataUtils.getString(R.string.error_internet_connection)
}
