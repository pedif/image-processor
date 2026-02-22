package com.techys.imagecapture.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.techys.imagecapture.R
import com.techys.ip.designsystem.theme.Dimen
import com.techys.ip.designsystem.theme.ImageProcessorTheme

@Composable
internal fun ActionAreaComponent(
    isImageCaptured: Boolean,
    modifier: Modifier = Modifier,
    onCaptureClick: () -> Unit = {},
    onRetryClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimen.paddingScreenHorizontal),
        horizontalArrangement = Arrangement.Center
    ) {
        if(isImageCaptured) {
            Button(
                onClick = onRetryClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.action_retry)
                )
            }

            Spacer(modifier = Modifier.width(Dimen.paddingLarge))
        }

        Button(
            onClick = onCaptureClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.action_capture)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewComponent() {
    ImageProcessorTheme {
        Surface {
            ActionAreaComponent(isImageCaptured = true)
        }
    }
}