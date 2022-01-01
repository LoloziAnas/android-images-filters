package com.lzitech.filterme.activities.savedImage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.lzitech.filterme.activities.editImage.EditImageActivity
import com.lzitech.filterme.activities.filteredImage.FilteredImageActivity
import com.lzitech.filterme.adapters.SavedImagesAdapter
import com.lzitech.filterme.databinding.ActivitySavedImageBinding
import com.lzitech.filterme.listeners.SavedImagesListener
import com.lzitech.filterme.utilities.displayToast
import com.lzitech.filterme.viewModels.SavedImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class SavedImageActivity : AppCompatActivity(), SavedImagesListener {

    private lateinit var binding: ActivitySavedImageBinding
    private val viewModel: SavedImageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setListeners()
        viewModel.loadSavedImages()
    }

    private fun setupObservers() {
        viewModel.savedImagesUiState.observe(this, {
            val savedImageDataState = it ?: return@observe
            binding.progressBar.visibility =
                if (savedImageDataState.isLoading) View.VISIBLE else View.GONE
            savedImageDataState.savedImages?.let { savedImages ->
                SavedImagesAdapter(savedImages, this).also { adapter ->
                    with(binding.recyclerViewSavedImages) {
                        this.adapter = adapter
                        visibility = View.VISIBLE
                    }
                }
            } ?: run {
                savedImageDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
    }

    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }

    }


    override fun onImageClick(file: File) {
        val fileUri =
            FileProvider.getUriForFile(applicationContext, "${packageName}.provider", file)
        Intent(applicationContext, FilteredImageActivity::class.java).also { filteredIntent ->
            filteredIntent.putExtra(EditImageActivity.FILTERED_IMAGE_URI_KEY, fileUri)
            startActivity(filteredIntent)
        }
    }

    override fun onDeleteImageClick(file: File) {
        if (file.exists()) {
            file.canonicalFile.delete()
        }
    }
}