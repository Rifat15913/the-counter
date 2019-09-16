package com.itechsoftsolutions.mtcore.main.data.remote.model

import com.google.gson.annotations.SerializedName
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareEntity
import com.itechsoftsolutions.mtcore.utils.helper.Constants

/**
 * This is model class for software collection
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class SoftwareCollection(
    @SerializedName(Constants.JsonKeys.SOFTWARES)
    var collection: List<SoftwareEntity>
)