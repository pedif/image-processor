package com.techys.ip.domain.usecase

import com.techys.ip.domain.model.ClassificationError
import com.techys.ip.domain.model.ClassificationResult
import com.techys.ip.domain.model.ImageLabel
import com.techys.ip.domain.repository.ImageRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.io.path.createTempFile

class ImageClassifyUseCaseTest {

    private lateinit var repo: ImageRepositoryFake
    private lateinit var useCase: ImageClassifyUseCase

    @BeforeEach
    fun setup() {
        repo = ImageRepositoryFake()
        useCase = ImageClassifyUseCase(repo)
    }

    @Test
    fun `returns error when file does not exist`() = runTest {
        // File existence is validated here to avoid repository calls with invalid input
        val fakeFile = File("this_does_not_exist.png")
        val result = useCase(fakeFile)
        assertTrue(result is ClassificationResult.Error)
        assertEquals(
            ClassificationError.FileNotFound,
            (result as ClassificationResult.Error).error
        )
    }

    @Test
    fun `returns error when repository fails`() = runTest {
        val tempFile = createTempFile().toFile()
        repo.result = ClassificationResult.Error(ClassificationError.ServerError)

        val result = useCase(tempFile)

        assertTrue(result is ClassificationResult.Error)
        assertEquals(
            ClassificationError.ServerError,
            (result as ClassificationResult.Error).error
        )
    }

    @Test
    fun `returns label when classification succeeds`() = runTest {
        val tempFile = createTempFile().toFile()
        val expectedLabel = ImageLabel(label = "cat", confidence = 1f)

        repo.result = ClassificationResult.Success(expectedLabel)
        val result = useCase(tempFile)

        assertTrue(result is ClassificationResult.Success)
        assertEquals(
            expectedLabel,
            (result as ClassificationResult.Success).label
        )
    }

}
