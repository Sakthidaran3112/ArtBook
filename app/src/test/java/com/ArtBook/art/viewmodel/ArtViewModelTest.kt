package com.ArtBook.art.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ArtBook.art.getOrAwaitValueTest
import com.ArtBook.art.repo.FakeArtRepository
import com.ArtBook.art.roomdb.Art
import com.ArtBook.art.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    private lateinit var viewModel : ArtViewModel

    @Mock
    private lateinit var observer: Observer<List<Art>>


    @Before
    fun setup() {
        viewModel = ArtViewModel(FakeArtRepository())
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain() // Reset main dispatcher after testing
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun `deleteArt removes item from artList`() = testCoroutineScope.runBlockingTest {
        val art = Art("Mona Lisa", "Da Vinci", 1503, "url")
        viewModel.insertArt(art)
        viewModel.deleteArt(art)

        viewModel.artList.observeForever(observer)

        // Assert and verify the observer's state
        assert(viewModel.artList.value?.contains(art) == false)

        viewModel.artList.removeObserver(observer)
    }

    @Test
    fun `insert art without year returns error`() {
        viewModel.makeArt("Mona Lisa","Da Vinci","")

        val value = viewModel.insertArtMessage.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.ERROR)
    }


    @Test
    fun `insert art without name returns error`() {
        viewModel.makeArt("","Da Vinci","1500")

        val value = viewModel.insertArtMessage.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`() {
        viewModel.makeArt("Mona Lisa","","1500")

        val value = viewModel.insertArtMessage.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.ERROR)
    }


}