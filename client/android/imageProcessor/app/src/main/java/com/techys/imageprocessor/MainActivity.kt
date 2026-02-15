package com.techys.imageprocessor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.techys.classification.screen.ClassificationScreen
import com.techys.classification.viewmodel.ClassificationViewModel
import com.techys.ip.designsystem.theme.ImageProcessorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val container = (application as Application).appContainer
        setContent {
            ImageProcessorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = remember {
                        ClassificationViewModel(
                            classificationUseCase = container.classificationUseCase,
                            imagePreparer = container.imagePreparer,
                            dispatcher = container.dispatcher,
                            logger = container.logger
                        )
                    }
                    ClassificationScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
