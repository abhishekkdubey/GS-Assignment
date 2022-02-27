package com.goldmansanch.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldmansanch.data.APODItem
import com.goldmansanch.data.Error
import com.goldmansanch.repository.ApodRepository
import com.goldmansanch.util.DateUtil
import com.golmansanch.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apodRepository: ApodRepository,
    private val dateUtil: DateUtil
) : ViewModel()
{

    init
    {
        fetchAPOD(dateUtil.getTodayDate())
    }

    private val _apod = MutableLiveData<APODItem>()
    private val _error = MutableLiveData<Pair<Boolean, Error>>()

    val apod: LiveData<APODItem> = _apod

    val error: LiveData<Pair<Boolean, Error>> = _error


    fun getTodayDate(): String{
     return  dateUtil.getTodayDate()
    }
    fun fetchAPOD(date: String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            apodRepository.getAPODByDate(date) { result ->
                if (result.data!=null && result.isSuccess)
                {
                    _error.postValue(Pair(false, getError(result.code, result.message)))
                    _apod.postValue(result.data!!)
                } else
                {
                    _error.postValue(
                        Pair(
                            true,
                            getError(result.code, result.message)
                        )
                    )
                }
            }
        }
    }

    private fun getError(code: String?, s: String?): Error
    {
       return when(code){
            "400"->{
                Error(s?:"No Internet! Check your connection", R.drawable.ic_no_connection)
            }
            else ->{
                Error(s?:"Something not right!!, Please try Again", R.drawable.ic_generic_error)
            }
        }
    }

    fun markFavourite(isFavourite: Boolean)
    {
        viewModelScope.launch(Dispatchers.IO) {
            apod.value?.run {
                apodRepository.markFavourite(this, isFavourite)
                isFav = isFavourite
                _apod.postValue(this)
            }
        }
    }


}