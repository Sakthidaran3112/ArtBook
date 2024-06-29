package com.ArtBook.art.repo

import androidx.lifecycle.LiveData
import com.ArtBook.art.model.ImageResponse
import com.ArtBook.art.roomdb.Art
import com.ArtBook.art.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>

}