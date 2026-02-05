package com.techys.classification.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.techys.classification.R
import com.techys.ip.designsystem.theme.Dimen
import com.techys.ip.designsystem.theme.ImageProcessorTheme

@Composable
fun ActionAreaComponent(
    isImageSelected: Boolean,
    modifier: Modifier = Modifier,
    onImagePicked: (Uri) -> Unit = {},
    onClassifyClick: () -> Unit = {}
) {
    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            uri?.let(onImagePicked)
        }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClassifyClick,
            enabled = isImageSelected
        ) {
            Text(text = stringResource(R.string.action_classification))
        }
        Spacer(modifier = Modifier.height(Dimen.paddingLarge))
        Button(onClick = {
            imagePickerLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }) {
            Text(stringResource(R.string.action_pick_gallery))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewComponent() {
    ImageProcessorTheme {
        Surface {
            ActionAreaComponent(
                isImageSelected = true
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewComponentNoImage() {
    ImageProcessorTheme {
        Surface {
            ActionAreaComponent(
                isImageSelected = false
            )
        }
    }
}