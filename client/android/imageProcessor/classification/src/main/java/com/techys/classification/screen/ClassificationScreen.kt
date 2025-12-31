package com.techys.classification.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.techys.classification.R
import com.techys.classification.model.ClassificationState
import com.techys.classification.viewmodel.ClassificationViewModel
import com.techys.ip.designsystem.theme.Dimen
import com.techys.ip.designsystem.theme.ImageProcessorTheme

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
            Toast.makeText(context, "Starting classifying", Toast.LENGTH_SHORT).show()
        }
    )

    val errorFlow = viewModel.errorMessages
    LaunchedEffect(errorFlow) {
        errorFlow.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT)
        }
    }
}

@Composable
private fun ClassificationScreen(
    state: ClassificationState,
    modifier: Modifier = Modifier,
    onClassifyClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = Dimen.paddingScreenVertical,
                horizontal = Dimen.paddingScreenHorizontal
            )
    ) {
        Button(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onClassifyClick
        ) {
            Text(text = stringResource(R.string.action_classification))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreen() {
    ImageProcessorTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ClassificationScreen(ClassificationState())
        }
    }
}