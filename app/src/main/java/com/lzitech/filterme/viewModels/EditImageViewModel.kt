package com.lzitech.filterme.viewModels

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lzitech.filterme.data.ImageFilter
import com.lzitech.filterme.repositories.EditImageRepository
import com.lzitech.filterme.utilities.Coroutines

class EditImageViewModel(private val editImageRepository: EditImageRepository) : ViewModel() {
    // region:: Prepare Image Preview
    private val imagePreviewDataStore = MutableLiveData<ImagePreviewDataStore>()
    val imagePreviewUiState: LiveData<ImagePreviewDataStore> get() = imagePreviewDataStore

    fun prepareImagePreview(imageUri: Uri) {
        Coroutines.io {
            runCatching {
                emitImagePreviewState(true)
                editImageRepository.prepareImagePreview(imageUri)
            }.onSuccess { bitmap ->
                if (bitmap != null) {
                    emitImagePreviewState(bitmap = bitmap)
                } else {
                    emitImagePreviewState(error = "Unable to prepare image preview")
                }
            }.onFailure {
                emitImagePreviewState(error = it.message.toString())
            }
        }
    }

    private fun emitImagePreviewState(
        isLoading: Boolean = false,
        bitmap: Bitmap? = null,
        error: String? = null
    ) {
        val dataState = ImagePreviewDataStore(isLoading, bitmap, error)
        imagePreviewDataStore.postValue(dataState)
    }

    data class ImagePreviewDataStore(
        val isLoading: Boolean,
        val bitmap: Bitmap?,
        val error: String?,
    )

    // endregion

    // region:: Load Image Filters
    private val imageFiltersDataState = MutableLiveData<ImageFiltersDataState>()
    val imageFiltersUiState: LiveData<ImageFiltersDataState> get() = imageFiltersDataState

    private fun getImagePreview(originalImage: Bitmap): Bitmap {
        return runCatching {
            val previewWidth = 150
            val previewHeight = originalImage.height * previewWidth / originalImage.width
            Bitmap.createScaledBitmap(originalImage, previewWidth, previewHeight, false)
        }.getOrDefault(originalImage)
    }

     fun loadImageFilters(originalImage: Bitmap) {
        Coroutines.io {
            runCatching {
                emitImageFiltersUiState(isLoading = true)
                editImageRepository.getImageFilters(getImagePreview(originalImage))
            }.onSuccess { imageFilters ->
                emitImageFiltersUiState(imageFilters = imageFilters)
            }.onFailure {
                emitImageFiltersUiState(error = it.message.toString())
            }
        }
    }

    private fun emitImageFiltersUiState(
        isLoading: Boolean = false,
        imageFilters: List<ImageFilter>? = null,
        error: String? = null
    ) {
        val dataState = ImageFiltersDataState(isLoading, imageFilters, error)
        imageFiltersDataState.postValue(dataState)
    }

    data class ImageFiltersDataState(
        val isLoading: Boolean,
        val imageFilters: List<ImageFilter>?,
        val error: String?
    )
    //endregion

    // region:: Save Filtered Image
    private val saveFilteredImageDataState = MutableLiveData<SaveFilteredImageDataState>()
    val saveFilteredImageUiState: LiveData<SaveFilteredImageDataState> get() = saveFilteredImageDataState

    fun saveFilteredImage(filteredImage: Bitmap) {
        Coroutines.io {
            runCatching {
                emitImageFiltersUiState(isLoading = true)
                editImageRepository.saveFilteredImage(filteredImage)

            }.onSuccess { savedImageUri ->
                emitSaveFilteredImageUiState(uri = savedImageUri)
            }.onFailure { error ->
                emitSaveFilteredImageUiState(error = error.message)
            }
        }
    }

    private fun emitSaveFilteredImageUiState(
        isLoading: Boolean = false,
        uri: Uri? = null,
        error: String? = null
    ) {
        val dataState = SaveFilteredImageDataState(isLoading, uri, error)
        saveFilteredImageDataState.postValue(dataState)
    }

    data class SaveFilteredImageDataState(
        val isLoading: Boolean,
        val uri: Uri?,
        val error: String?
    )
    // endregion
}