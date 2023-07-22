package com.omrilhn.artbookkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.omrilhn.artbookkotlin.databinding.ActivityArtBinding

class ArtActivity : AppCompatActivity() {

    private lateinit var binding : ActivityArtBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fun saveButtonClicked(view:View)
        {

        }
        fun selectImage(view:View)
        {

        }
    }
}