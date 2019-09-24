package com.diaryofrifat.thecounter.utils.helper

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import io.fabric.sdk.android.services.network.UrlUtils.urlEncode


object ShareUtils {

    /**
     * This method shares url on Facebook. Using Facebook app if installed or web link otherwise.
     *
     * @param activity activity which launches the intent
     * @param url url to share
     */
    fun shareUrlToFacebook(activity: Activity, url: String) {
        var facebookAppFound = false

        var shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url))

        val activityList = activity.packageManager.queryIntentActivities(shareIntent, 0)

        for (app in activityList) {
            if (app.activityInfo.packageName.contains(Constants.Common.FACEBOOK_ANDROID_APP_PACKAGE_PREFIX)) {
                val activityInfo = app.activityInfo
                val name = ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name)
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                shareIntent.component = name
                facebookAppFound = true
                break
            }
        }

        if (!facebookAppFound) {
            val sharerUrl = Constants.Common.FACEBOOK_SHARING_URL + url
            shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl))
        }

        activity.startActivity(shareIntent)
    }

    /**
     * This method shares tweet on Twitter. Using Twitter app if installed or web link otherwise.
     *
     * @param activity activity which launches the intent
     * @param text text to share
     * @param url url to share
     * @param via twitter username without '@' who shares
     * @param hashtags hashtags for tweet without '#' and separated by ','
     */
    fun shareToTwitter(activity: Activity, text: String?, url: String?, via: String?, hashtags: String?) {
        val tweetUrl = StringBuilder(Constants.Common.TWEET_URL)
        tweetUrl.append(
            if (TextUtils.isEmpty(text)) {
                urlEncode(Constants.Default.SPACE_STRING)
            } else {
                urlEncode(text)
            }
        )

        if (!TextUtils.isEmpty(url)) {
            tweetUrl.append(Constants.Common.TWITTER_APPENDING_URL_PREFIX)
            tweetUrl.append(urlEncode(url))
        }

        if (!TextUtils.isEmpty(via)) {
            tweetUrl.append(Constants.Common.TWITTER_APPENDING_VIA_PREFIX)
            tweetUrl.append(urlEncode(via))
        }

        if (!TextUtils.isEmpty(hashtags)) {
            tweetUrl.append(Constants.Common.TWITTER_APPENDING_HASHTAGS_PREFIX)
            tweetUrl.append(urlEncode(hashtags))
        }

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl.toString()))
        val matches = activity.packageManager.queryIntentActivities(intent, 0)
        for (info in matches) {
            if (info.activityInfo
                    .packageName
                    .toLowerCase()
                    .startsWith(Constants.Common.TWITTER_ANDROID_APP_PACKAGE_PREFIX)
            ) {
                intent.setPackage(info.activityInfo.packageName)
            }
        }

        activity.startActivity(intent)
    }
}