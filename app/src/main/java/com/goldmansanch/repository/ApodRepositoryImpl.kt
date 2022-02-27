package com.goldmansanch.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.goldmansanch.data.APODItem
import com.goldmansanch.network.NASAApi
import com.goldmansanch.storage.ApodDao
import com.goldmansanch.storage.data.Apod
import com.goldmansanch.util.Constants
import com.goldmansanch.util.DateUtil
import com.goldmansanch.util.Keys
import com.goldmansanch.util.NetworkUtil
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(
    private val key: Keys,
    private val nasaApi: NASAApi,
    private val apdDao: ApodDao,
    private val preference: SharedPreferences,
    private val networkUtil: NetworkUtil
    ) : ApodRepository
{

    /**
     * This function will fetch the APOD by date.
     * First, It will check that it's available in today's APOD cache in case of null.
     * It'll check if it's available in Favourite cache. In case it's not found in any cache
     * It'll call API to get the APOD data and store in
     */
    override suspend fun getAPODByDate(
        date: String,
        callback: (result: com.goldmansanch.data.Response<APODItem>) -> Unit
    )
    {

        var apod: APODItem? = isAPODAvailableInTodayCache(date)
        if (apod == null)
        {
            apod = getAPODFromDBCache(date)
        }
        if (apod != null)
        {
            callback.invoke(com.goldmansanch.data.Response.success(apod))
            return
        }
        if (!networkUtil.isConnectedToInternet()){
            callback.invoke(com.goldmansanch.data.Response.failure("400", "No Internet Connection!!"))
            return
        }

        nasaApi.getAPODData(key.apodKey(), date).enqueue(object : retrofit2.Callback<Apod>
        {
            override fun onResponse(call: Call<Apod>, response: Response<Apod>)
            {
                if (response.isSuccessful)
                {
                    val responseData = response.body()
                    if (responseData != null)
                    {
                        if (DateUtil.getTodayDate()==response.body()?.date)
                        {
                            cacheAPODData(responseData)
                        }
                        callback.invoke(
                            com.goldmansanch.data.Response.success(responseData.toAPODItem(false))
                        )
                    } else
                    {
                        callback.invoke(
                            com.goldmansanch.data.Response.failure(
                                response.code().toString(),
                                null
                            )
                        )
                    }
                } else
                {
                    val error = response.errorBody()?.string() ?: ""
                    callback.invoke(
                        com.goldmansanch.data.Response.failure(
                            response.code().toString(),
                            error
                        )
                    )
                }
            }

            override fun onFailure(call: Call<Apod>, t: Throwable)
            {
                callback.invoke(com.goldmansanch.data.Response.failure("", t.message ?: ""))
            }
        })
    }

    /**
     * Retrive Live data of Favourite APOD items and return the same
     */
    override suspend fun getFavAPODList(): LiveData<List<APODItem>>
    {
       return apdDao.getFavouriteApodLiveData().map {  it.map { it.toAPODItem(true) } }
    }


    override suspend fun markFavourite(apod: APODItem, isFav: Boolean): Long
    {
        return if (isFav) apdDao.insert(apod.toApod())
        else deleteFav(apod).toLong()
    }

    override suspend fun deleteFav(apod: APODItem): Int
    {
        return apdDao.delete(apod.toApod())
    }


    /**
     * Helper function to store only today's APOD cache
     */
    private fun isAPODAvailableInTodayCache(date: String): APODItem?
    {
        val apod: Apod? =
            Gson().fromJson(preference.getString(Constants.TODAYS_APOD_KEY, ""), Apod::class.java)
        return if (apod != null && apod.date == date)
        {
            apod.toAPODItem( apdDao.checkIfAPODAvailableNyDate(date) > 0)
        } else
        {
            null
        }
    }

    private fun getAPODFromDBCache(date: String): APODItem?
    {
        return apdDao.getAPODByDate(date)?.toAPODItem(true)
    }

    private fun cacheAPODData(apod: Apod?)
    {
        return with(preference.edit()) {
            if (apod != null)
            {
                putString(Constants.TODAYS_APOD_KEY, Gson().toJson(apod))
                apply()
            }
        }
    }


    fun Apod.toAPODItem(isFav: Boolean) = APODItem(
        id= id,
        copyright = copyright,
        date = date,
        explanation = explanation,
        hdurl = hdurl,
        mediaType = mediaType,
        serviceVersion = serviceVersion,
        title = title,
        url = url,
        isFav = isFav

    )
    fun APODItem.toApod() = Apod(
        id= id,
        copyright = copyright,
        date = date,
        explanation = explanation,
        hdurl = hdurl,
        mediaType = mediaType,
        serviceVersion = serviceVersion,
        title = title,
        url = url
    )
}