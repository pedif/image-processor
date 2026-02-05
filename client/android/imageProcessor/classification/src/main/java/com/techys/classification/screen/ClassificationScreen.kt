package com.techys.classification.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.techys.classification.model.ClassificationState
import com.techys.classification.model.ImageSource
import com.techys.classification.viewmodel.ClassificationViewModel
import com.techys.core.model.UiState
import com.techys.ip.designsystem.theme.Dimen
import com.techys.ip.designsystem.theme.ImageProcessorTheme
import com.techys.ip.domain.model.ImageLabel

@Composable
fun ClassificationScreen(
    viewModel: ClassificationViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    ClassificationScreen(
        state = state,
        modifier = modifier,
        onClassifyClick = {
            viewModel.classify()
        },
        onImagePicked = { imageUri ->
            viewModel.setImageSource(imageUri)
        }
    )

    val errorFlow = viewModel.errorMessages
    LaunchedEffect(errorFlow) {
        errorFlow.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun ClassificationScreen(
    state: ClassificationState,
    modifier: Modifier = Modifier,
    onClassifyClick: () -> Unit = {},
    onImagePicked: (Uri) -> Unit = {}
) {

    val label: ImageLabel? = state.label
    val image: ImageSource? = state.image
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        ContentArea(
            image = image,
            label = label
        )
        ActionAreaComponent(
            isImageSelected = image != null,
            modifier = Modifier
                .padding(
                    vertical = Dimen.paddingScreenVertical,
                    horizontal = Dimen.paddingScreenHorizontal
                )
                .align(Alignment.BottomCenter),
            onClassifyClick = onClassifyClick,
            onImagePicked = onImagePicked
        )

        if (state.uiState == UiState.Loading)
            LoadingComponent()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreen() {
    ImageProcessorTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ClassificationScreen(
                ClassificationState(
                    label = ImageLabel("this is a cat", 0.87f)
                )
            )
        }
    }
}
