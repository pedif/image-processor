package com.techys.imageprocessor.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.techys.classification.viewmodel.ClassificationViewModel
import com.techys.imagecapture.screen.ImageCaptureScreen
import com.techys.imagecapture.viewmodel.ImageCaptureViewmodel
import com.techys.imageprocessor.Application
import com.techys.imageprocessor.nav.NavHost
import com.techys.ip.designsystem.theme.ImageProcessorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageProcessorTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}