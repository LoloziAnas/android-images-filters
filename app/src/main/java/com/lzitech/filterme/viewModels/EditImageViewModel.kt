package com.lzitech.filterme.viewModels

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lzitech.filterme.repositories.EditImageRepository
import com.lzitech.filterme.utilities.Coroutines

class EditImageViewModel(private val editImageRepository: EditImageRepository) : ViewModel() {
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
}