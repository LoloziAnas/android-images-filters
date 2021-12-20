package com.lzitech.filterme.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lzitech.filterme.R
import com.lzitech.filterme.data.ImageFilter
import com.lzitech.filterme.databinding.ItemContainerFiltersBinding
import com.lzitech.filterme.listeners.ImageFiltersListener

class ImageFiltersAdapter(
    private val imageFilters: List<ImageFilter>,
    private val imageFiltersListener: ImageFiltersListener
) :
    RecyclerView.Adapter<ImageFiltersAdapter.ImageFiltersViewHolder>() {
    private var selectedFilterPosition = 0
    private var previouslySelectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFiltersViewHolder {
        val binding =
            ItemContainerFiltersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageFiltersViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ImageFiltersViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        with(holder) {
            with(imageFilters[position]) {
                binding.imageFilterPreview.setImageBitmap(filterPreview)
                binding.textFilterName.text = name
                binding.root.setOnClickListener {
                    if (position != selectedFilterPosition) {
                        imageFiltersListener.onFilterSelected(this)
                        previouslySelectedPosition = selectedFilterPosition
                        selectedFilterPosition = position
                        with(this@ImageFiltersAdapter) {
                            notifyItemChanged(previouslySelectedPosition, Unit)
                            notifyItemChanged(selectedFilterPosition, Unit)
                        }
                    }
                }
            }
            binding.textFilterName.setTextColor(
                ContextCompat.getColor(
                    binding.textFilterName.context,
                    if (selectedFilterPosition == position)
                        R.color.primaryDark
                    else
                        R.color.primaryText
                )
            )
        }
    }

    override fun getItemCount() = imageFilters.size

    inner class ImageFiltersViewHolder(val binding: ItemContainerFiltersBinding) :
        RecyclerView.ViewHolder(binding.root)
}