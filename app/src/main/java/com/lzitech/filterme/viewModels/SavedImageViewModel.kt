package com.lzitech.filterme.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lzitech.filterme.repositories.SavedImageRepository
import com.lzitech.filterme.utilities.Coroutines
import java.io.File

class SavedImageViewModel(private val savedImageRepository: SavedImageRepository) : ViewModel() {
    private val savedImagesDataState = MutableLiveData<SavedImageDataState>()
    val savedImagesUiState: LiveData<SavedImageDataState> get() = savedImagesDataState

    fun loadSavedImages() {
        Coroutines.io {
            runCatching {
                emitSavedImageUiState(isLoading = true)
                savedImageRepository.loadSavedImages()
            }.onSuccess { savedImages ->
                if (savedImages.isNullOrEmpty()) {
                    emitSavedImageUiState(error = "No Images Found")
                } else {
                    emitSavedImageUiState(savedImages = savedImages)
                }
            }.onFailure { error ->
                emitSavedImageUiState(error = error.message)
            }
        }
    }

    private fun emitSavedImageUiState(
        isLoading: Boolean = false,
        savedImages: List<Pair<File, Bitmap>>? = null,
        error: String? = null
    ) {
        val dataState = SavedImageDataState(isLoading, savedImages, error)
        savedImagesDataState.postValue(dataState)
    }

    data class SavedImageDataState(
        val isLoading: Boolean,
        val savedImages: List<Pair<File, Bitmap>>?,
        val error: String?
    )
}