package com.techys.imageprocessor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.techys.classification.screen.ClassificationScreen
import com.techys.classification.viewmodel.ClassificationViewModel
import com.techys.core.di.AppContainer
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
                            dispatcher = container.dispatcher,
                            logger = container.Logger
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
