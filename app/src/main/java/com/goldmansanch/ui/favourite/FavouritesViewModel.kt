package com.goldmansanch.ui.favourite

import androidx.lifecycle.*
import com.goldmansanch.data.APODItem
import com.goldmansanch.data.Error
import com.goldmansanch.storage.data.Apod
import com.goldmansanch.repository.ApodRepository
import com.golmansanch.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val apodRepository: ApodRepository
) : ViewModel()
{

    suspend fun getFavouriteAPODLive(): LiveData<List<APODItem>>
    {
        return viewModelScope.async {
            return@async apodRepository.getFavAPODList()
        }.await()
    }

    fun removeFavouriteAPOD(apod: APODItem)
    {
        viewModelScope.launch {
            apodRepository.deleteFav(apod)
        }
    }
}