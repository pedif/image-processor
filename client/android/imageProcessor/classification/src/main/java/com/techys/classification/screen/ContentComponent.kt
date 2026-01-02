package com.techys.classification.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.techys.classification.R
import com.techys.classification.model.ImageSource
import com.techys.ip.designsystem.theme.Color
import com.techys.ip.designsystem.theme.Dimen
import com.techys.ip.domain.model.ImageLabel
import kotlin.math.roundToInt

@Composable
internal fun ContentArea(
    image: ImageSource?,
    label: ImageLabel?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageBitmap: ImageBitmap? = remember(image) {
        image?.uri?.let { uri -> loadImageBitmapFromAsset(context, uri) }
    }
    if (imageBitmap == null)
        return
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
        if (label != null) {
            val confidencePercentage = (label.confidence * 100).roundToInt()
            Text(
                modifier = Modifier
                    .padding(top = Dimen.paddingHuge)
                    .align(Alignment.TopCenter)
                    .background(Color.greySemiTransparent)
                    .padding(
                        Dimen.paddingMedium
                    ),
                text = stringResource(
                    R.string.classification_result,
                    label.label,
                    confidencePercentage
                )
            )
        }
    }
}