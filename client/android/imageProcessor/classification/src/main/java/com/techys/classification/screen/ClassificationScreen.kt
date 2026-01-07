package com.techys.classification.screen

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.techys.classification.R
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
            viewModel.classifyDemoAsset(context)
        }
    )

    val errorFlow = viewModel.errorMessages
    LaunchedEffect(errorFlow) {
        errorFlow.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    //TODO delete when gallery/cameraX implemented
    LaunchedEffect(Unit) {
        viewModel.loadAssetPlaceholder()
    }
}

@Composable
private fun ClassificationScreen(
    state: ClassificationState,
    modifier: Modifier = Modifier,
    onClassifyClick: () -> Unit = {}
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
        Box(
            modifier = Modifier
                .padding(
                    vertical = Dimen.paddingScreenVertical,
                    horizontal = Dimen.paddingScreenHorizontal
                )
                .align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = onClassifyClick
            ) {
                Text(text = stringResource(R.string.action_classification))
            }
        }

        if(state.uiState == UiState.Loading)
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

//TODO only used for testing purposes. Delete when gallery/Camera are setup
fun loadImageBitmapFromAsset(
    context: Context,
    assetPath: Uri
): ImageBitmap {
    val inputStream = context.assets.open(assetPath.path.orEmpty())
    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream.close()
    return bitmap.asImageBitmap()
}
