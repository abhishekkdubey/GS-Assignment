package com.goldmansanch.util

import com.goldmansanch.data.APODItem
import com.goldmansanch.storage.data.Apod

/**ø
 * Created by Abhishek on 27,February,2022 For GolmanSanch
 *
 */
object StringUtil
{

    fun getYoutubeVideoThumbnailURL(url: String?): String?
    {
        return url?.let { "https://img.youtube.com/vi${getVideoId(url)}/0.jpg" }
    }


    private fun getVideoId(url: String): String?
    {
        return if (url.contains('?') && url.contains('/'))
        {
            url.substring(url.lastIndexOf("/"), url.lastIndexOf("?"))
        } else
        {
            null
        }

    }
}