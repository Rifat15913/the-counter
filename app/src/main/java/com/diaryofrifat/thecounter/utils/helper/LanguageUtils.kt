package com.diaryofrifat.thecounter.utils.helper

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.LocaleList
import java.util.*

/**
 * This is a singleton class that contains utils to work with language.
 *
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
object LanguageUtils {
    private const val SELECTED_LANGUAGE = "selected_language"

    /**
     * This method sets application locale with default locale persisted in [SharedPreferences] on
     * each new launch of the application and returns Context having application default locale
     * for all activities
     *
     * @param context application context
     * @return modified application context
     * */
    fun onAttach(context: Context, defaultLanguage: String = Locale.getDefault().language): Context {
        val language = getStoredLanguage(defaultLanguage)
        return setLanguage(context, language)
    }

    /**
     * This method returns language code stored at [SharedPreferences]
     *
     * @return stored language
     * */
    fun getLanguage(): String {
        return getStoredLanguage(Locale.getDefault().language)
    }

    /**
     * This method stores new language code [SharedPreferences] and
     * updates application default locale. Returns context having application default locale.
     *
     * @param context application context
     * @param language language code
     * @return modified application context
     * */
    fun setLanguage(context: Context, language: String): Context {
        storeLanguage(language)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else {
            updateResourcesLegacy(context, language)
        }
    }

    /**
     * This method stores new language code [SharedPreferences] and
     * updates application default locale. Returns context having application default locale.
     *
     * @param intent intent with target activity to be restarted
     * @param context application context
     * @param language language code
     * @return modified application context
     * */
    fun setLanguageAndRestartApplication(intent: Intent, context: Context, language: String): Context {
        val modifiedContext = setLanguage(context, language)
        AndroidUtils.restartApplication(intent, context)
        return modifiedContext
    }

    /**
     * This method returns language code persisted in [SharedPreferences]
     *
     * @param defaultLanguage default language code if no language code is found in the storage
     * @return language code
     * */
    fun getStoredLanguage(defaultLanguage: String): String {
        return SharedPrefUtils.readString(SELECTED_LANGUAGE, defaultLanguage)
    }

    /**
     * This method stores new language code at [SharedPreferences]
     *
     * @param language language code
     * @return if the language code was saved or not
     * */
    private fun storeLanguage(language: String): Boolean {
        return SharedPrefUtils.write(SELECTED_LANGUAGE, language)
    }

    /**
     * For android device versions above Nougat (7.0)
     * This method updates application default locale configurations and returns new context object
     * for the current context but whose resources are adjusted to match the given Configuration
     *
     * @param context application context
     * @param language language code
     * @return modified application context
     * */
    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val localeList = LocaleList(locale)

        val config = context.resources.configuration
        config.locales = localeList

        return context.createConfigurationContext(config)
    }

    /**
     * For android device versions below Nougat (7.0)
     * This method updates application default locale configurations and returns new context object
     * for the current context but whose resources are adjusted to match the given Configuration
     *
     * @param context application context
     * @param language language code
     * @return modified application context
     * */
    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources

        val config = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)

        return context
    }
}
