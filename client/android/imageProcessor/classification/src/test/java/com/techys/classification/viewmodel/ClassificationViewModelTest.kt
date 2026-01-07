package com.techys.classification.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.techys.common.util.JavaLogger
import com.techys.core.model.UiState
import com.techys.ip.domain.repository.ImageRepositoryStub
import com.techys.ip.domain.usecase.ImageClassifyUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import testutils.MainDispatcherRule
import kotlin.io.path.createTempFile
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class ClassificationViewModelTest {
    lateinit var viewModel: ClassificationViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        val repository = ImageRepositoryStub()
        val useCase = ImageClassifyUseCase(repository)
        viewModel = ClassificationViewModel(
            classificationUseCase = useCase,
            dispatcher = mainDispatcherRule.dispatcher,
            logger = JavaLogger()
        )
    }

    @Test
    fun `calling classify correctly changes the ui states`() = runTest {
        viewModel.state.test(timeout = 5.seconds) {

            viewModel.classify(createTempFile().toFile())

            assertThat(awaitItem().state).isEqualTo(UiState.Idle)
            assertThat(awaitItem().state).isEqualTo(UiState.Loading)
            assertThat(awaitItem().state).isEqualTo(UiState.Idle)

            cancelAndConsumeRemainingEvents() // cleanup
        }
    }
}
