package com.ArtBook.art.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ArtBook.art.model.ImageResponse
import com.ArtBook.art.roomdb.Art
import com.ArtBook.art.util.Resource

class FakeArtRepositoryAndroid : ArtRepositoryInterface {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshLiveData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshLiveData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshLiveData() {
        artsLiveData.postValue(arts)
    }


}