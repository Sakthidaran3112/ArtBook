package com.example.artbook.model

import com.example.artbook.model.ImageResult

data class ImageResponse(
        val hits: List<ImageResult>,
        val total: Int,
        val totalHits: Int
)
