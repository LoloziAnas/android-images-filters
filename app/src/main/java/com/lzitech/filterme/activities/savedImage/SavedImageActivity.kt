package com.lzitech.filterme.activities.savedImage

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lzitech.filterme.databinding.ActivitySavedImageBinding
import com.lzitech.filterme.utilities.displayToast
import com.lzitech.filterme.viewModels.SavedImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedImageActivity : AppCompatActivity() {

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
                displayToast("${savedImages.size} loaded images")
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
}