package com.example.artbook.viewmodel

import android.net.http.UrlRequest.Status
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.artbook.MainCoroutineRule
import com.example.artbook.getOrAwaitValueTest
import com.example.artbook.repo.FakeArtRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup (){

        //Test Doubles
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `art without year error`(){
        viewModel.makeArt("Mona Lisa", "Da Vinci", "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(com.example.artbook.util.Status.ERROR)
    }

    @Test
    fun `art without name error`(){
        viewModel.makeArt("", "Da Vinci", "2023")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(com.example.artbook.util.Status.ERROR)
    }
    @Test
    fun `art without artistName error`(){
        viewModel.makeArt("Mona Lisa", "", "2023")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(com.example.artbook.util.Status.ERROR)
    }



}