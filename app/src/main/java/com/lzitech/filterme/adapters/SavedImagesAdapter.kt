package com.lzitech.filterme.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzitech.filterme.databinding.ItemContainerSavedImagesBinding
import com.lzitech.filterme.listeners.SavedImagesListener
import java.io.File

class SavedImagesAdapter(
    private val savedImages: List<Pair<File, Bitmap>>,
    private val savedImagesListener: SavedImagesListener
) :
    RecyclerView.Adapter<SavedImagesAdapter.SavedImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedImageViewHolder {
        val binding = ItemContainerSavedImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SavedImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedImageViewHolder, position: Int) {
        with(holder) {
            with(savedImages[position]) {
                binding.imageViewSaved.setImageBitmap(second)
                binding.imageViewSaved.setOnClickListener {
                    savedImagesListener.onImageClick(first)
                }
            }
        }
    }

    override fun getItemCount() = savedImages.size

    inner class SavedImageViewHolder(val binding: ItemContainerSavedImagesBinding) :
        RecyclerView.ViewHolder(
            binding.root
        )
}