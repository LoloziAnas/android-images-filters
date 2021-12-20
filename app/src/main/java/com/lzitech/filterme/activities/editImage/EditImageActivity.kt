package com.lzitech.filterme.activities.editImage

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.lzitech.filterme.activities.main.MainActivity
import com.lzitech.filterme.adapters.ImageFiltersAdapter
import com.lzitech.filterme.data.ImageFilter
import com.lzitech.filterme.databinding.ActivityEditImageBinding
import com.lzitech.filterme.listeners.ImageFiltersListener
import com.lzitech.filterme.utilities.displayToast
import com.lzitech.filterme.utilities.show
import com.lzitech.filterme.viewModels.EditImageViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageActivity : AppCompatActivity(), ImageFiltersListener {

    private lateinit var binding: ActivityEditImageBinding
    private val viewModel: EditImageViewModel by viewModel()
    private lateinit var gpuImage: GPUImage

    //Image bitmaps
    private lateinit var originalBitmap: Bitmap
    private val filteredBitmap = MutableLiveData<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        //displayImagePreview()
        setupObservers()
        prepareImagePreview()
    }

    private fun setupObservers() {
        viewModel.imagePreviewUiState.observe(this, {
            val dataState = it ?: return@observe
            binding.progressBar.visibility = if (dataState.isLoading) View.VISIBLE else View.GONE
            dataState.bitmap?.let { bitmap ->
                // for the first time original = filtered image
                originalBitmap = bitmap
                filteredBitmap.value = bitmap
                with(originalBitmap) {
                    gpuImage.setImage(this)
                    binding.imagePreview.show()
                    viewModel.loadImageFilters(bitmap)
                }
            } ?: kotlin.run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
        viewModel.imageFiltersUiState.observe(this, {
            val imageFiltersDataState = it ?: return@observe
            binding.progressImageFilters.visibility =
                if (imageFiltersDataState.isLoading) View.VISIBLE else View.GONE
            imageFiltersDataState.imageFilters?.let { imageFilters ->
                ImageFiltersAdapter(imageFilters, this).also { imageFiltersAdapter ->
                    binding.recyclerViewFilters.adapter = imageFiltersAdapter
                }
            } ?: kotlin.run {
                imageFiltersDataState.error?.let { error ->
                    displayToast(error)
                }
            }

        })
        filteredBitmap.observe(this, { bitmap ->
            binding.imagePreview.setImageBitmap(bitmap)
        })
    }

    private fun prepareImagePreview() {
        gpuImage = GPUImage(applicationContext)
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
            viewModel.prepareImagePreview(imageUri)
        }
    }

/*    private fun displayImagePreview() {
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
            val inputStream = contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.imagePreview.setImageBitmap(bitmap)
            binding.imagePreview.visibility = View.VISIBLE
        }
    }*/

    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
        // this will show original image when the user clicks a long click the imageView until we release click,
        // So that the user can see the difference between the original image and the filtered image
        binding.imagePreview.setOnLongClickListener {
            binding.imagePreview.setImageBitmap(originalBitmap)
            return@setOnLongClickListener false
        }
        binding.imagePreview.setOnClickListener {
            binding.imagePreview.setImageBitmap(filteredBitmap.value)
        }
    }

    override fun onFilterSelected(filterImage: ImageFilter) {
        with(filterImage) {
            with(gpuImage) {
                setFilter(filter)
                filteredBitmap.value = bitmapWithFilterApplied
            }
        }
    }
}