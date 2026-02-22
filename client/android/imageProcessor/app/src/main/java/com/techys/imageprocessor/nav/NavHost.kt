package com.techys.imageprocessor.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.techys.classification.screen.ClassificationScreen
import com.techys.classification.viewmodel.ClassificationViewModel
import com.techys.imagecapture.screen.ImageCaptureScreen
import com.techys.imagecapture.viewmodel.ImageCaptureViewmodel
import com.techys.imageprocessor.Application

@Composable
fun NavHost(
    modifier: Modifier = Modifier
) {

    val backStack = remember { mutableStateListOf<Any>(ClassifyRoute(imagePath = null)) }

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<ClassifyRoute> { key ->
                ClassificationScreen(
                    modifier = modifier,
                    onImageCaptureClick = {
                        backStack.add(ImageCaptureRoute)
                    }
                )
            }
            entry<ImageCaptureRoute> {
                ImageCaptureScreen(
                    modifier = modifier,
                    onImageSelectedCLick = { imagePath ->
                        backStack.clear()
                        backStack.add(ClassifyRoute(imagePath = imagePath))
                    }
                )
            }
        }
    )
}