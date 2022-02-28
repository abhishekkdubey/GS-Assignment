package com.goldmansanch.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Abhishek on 26,February,2022 For GolmanSanch
 *
 */
object DateUtil
{

    fun isTodaysDate(dateString: String?): Boolean
    {
        if (dateString==null){
            return false
        }
        val date: Date? = getSimpleDateFormat().parse(dateString)
        val millis: Long = date?.time ?: 0
        val c = Calendar.getInstance()
        c.timeInMillis = millis

        val cToday = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        return c.get(Calendar.DAY_OF_MONTH) == cToday.get(Calendar.DAY_OF_MONTH) &&
                c.get(Calendar.MONTH) == cToday.get(Calendar.MONTH) &&
                c.get(Calendar.YEAR) == c.get(Calendar.YEAR)
    }

    fun getDate(calendar: Calendar): String{
        return getSimpleDateFormat().format(calendar.time)

    }

    fun getTodayDate(): String{
        return getSimpleDateFormat().format(Calendar.getInstance(
            TimeZone.getTimeZone("UTC")).timeInMillis)
    }

    fun getSimpleDateFormat():SimpleDateFormat{
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf
    }

    fun isValidDate(timeInMilli: Long):Boolean{
        val startCalender = Calendar.getInstance()
        startCalender.set(Calendar.YEAR, 1995)
        startCalender.set(Calendar.MONTH, 6)
        startCalender.set(Calendar.DAY_OF_MONTH, 16)
        val endCalender = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        return timeInMilli>startCalender.timeInMillis && timeInMilli<endCalender.timeInMillis
    }

}