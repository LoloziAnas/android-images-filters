package com.lzitech.filterme.activities.savedImage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lzitech.filterme.databinding.ActivitySavedImageBinding

class SavedImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySavedImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}