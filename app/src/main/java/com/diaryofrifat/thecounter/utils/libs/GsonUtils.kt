package com.diaryofrifat.thecounter.utils.libs

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber

/**
 * This is a singleton class that contains utils to work with json. It uses a library
 * called [Gson] to operate json.
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
object GsonUtils {

    private val mGson = Gson()

    /**
     * This method converts a model class object to json String
     *
     * @param singleObject an object of model class
     * @return [String] the desired json
     *
     * i.e: String json = GsonUtils.toJson(singleObject)
     * */
    fun <T> toJson(singleObject: T): String? {
        val type = object : TypeToken<T>() {}.type

        try {
            return mGson.toJson(singleObject, type)
        } catch (e: Exception) {
            Timber.e(e)
        }

        return null
    }

    /**
     * This method converts a objects of model class object to json String
     *
     * @param objects a objects of model class object
     * @return [String] the desired json
     *
     * i.e: String json = GsonUtils.toJson(objects)
     * */
    fun <T> toJson(objects: List<T>): String? {
        val type = object : TypeToken<List<T>>() {}.type

        try {
            return mGson.toJson(objects, type)
        } catch (e: Exception) {
            Timber.e(e)
        }

        return null
    }

    /**
     * This method converts a map of model class object to json String
     *
     * @param objects an map of model class objects
     * @return [String] the desired json
     *
     * i.e: String json = GsonUtils.toJson(objects)
     * */
    fun <T1, T2> toJson(objects: Map<T1, T2>): String? {
        val type = object : TypeToken<Map<T1, T2>>() {}.type

        try {
            return mGson.toJson(objects, type)
        } catch (e: Exception) {
            Timber.e(e)
        }

        return null
    }

    /**
     * This method converts an array of model class object to json String
     *
     * @param objects an array of objects of model class
     * @return [String] the desired json
     *
     * i.e: String json = GsonUtils.toJson(objects)
     * */
    fun <T> toJson(objects: Array<T>): String? {
        val type = object : TypeToken<Array<T>>() {}.type

        try {
            return mGson.toJson(objects, type)
        } catch (e: Exception) {
            Timber.e(e)
        }

        return null
    }

    /**
     * This method converts a json string to a list of model class object
     *
     * @param jsonString the string from which the object is to be de-serialized
     * @return desired object
     * */
    inline fun <reified T> fromJson(jsonString: String): T? {
        return try {
            Gson().fromJson<T>(jsonString, object : TypeToken<T>() {}.type)
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}
