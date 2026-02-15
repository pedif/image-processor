package com.techys.classification.viewmodel

import android.net.Uri
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.techys.classification.R
import com.techys.common.util.JavaLogger
import com.techys.core.model.UiState
import com.techys.ip.domain.repository.ImageRepositoryStub
import com.techys.ip.domain.usecase.ImageClassifyUseCase
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import testutils.MainDispatcherRule
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class ClassificationViewModelTest {
    lateinit var viewModel: ClassificationViewModel
    val testUri = mockk<Uri>(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    fun setUp(imagePreparer: FakeImagePreparer) {
        val repository = ImageRepositoryStub()
        val useCase = ImageClassifyUseCase(repository)
        viewModel = ClassificationViewModel(
            classificationUseCase = useCase,
            imagePreparer = imagePreparer,
            dispatcher = mainDispatcherRule.dispatcher,
            logger = JavaLogger()
        )
    }

    @Test
    fun `calling classify correctly changes the ui states`() = runTest {
        val imagePreparer = FakeImagePreparer(shouldFail = false)
        setUp(imagePreparer)
        viewModel.state.test(timeout = 5.seconds) {

            viewModel.classify(imagePreparer.prepare(testUri))

            assertThat(awaitItem().uiState).isEqualTo(UiState.Idle)
            assertThat(awaitItem().uiState).isEqualTo(UiState.Loading)
            assertThat(awaitItem().uiState).isEqualTo(UiState.Idle)

            cancelAndConsumeRemainingEvents() // cleanup
        }
    }

    @Test
    fun`classify with no source emits nothing selected error`() = runTest{
        val imagePreparer = FakeImagePreparer(shouldFail = false)
        setUp(imagePreparer)
        viewModel.errorMessages.test(timeout = 5.seconds) {
            viewModel.classify()
            assertThat(awaitItem()).isEqualTo(R.string.error_nothing_selected)
        }
    }

    @Test
    fun `classify emits file error when image preparation fails`() = runTest {
        val imagePreparer = FakeImagePreparer(shouldFail = true)
        setUp(imagePreparer)
        viewModel.setImageSource(testUri)
        viewModel.errorMessages.test(timeout = 5.seconds) {
            viewModel.classify()
            assertThat(awaitItem()).isEqualTo(R.string.error_classification_file_error)
        }
    }
}
