package com.diaryofrifat.thecounter.utils.helper.network

import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import java.io.IOException

/**
 * This is an exception class for no connectivity of internet
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
class NoConnectivityException : IOException() {
    override val message: String
        get() = DataUtils.getString(R.string.error_internet_connection)
}
