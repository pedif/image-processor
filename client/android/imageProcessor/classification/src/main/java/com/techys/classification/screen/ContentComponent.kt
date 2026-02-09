package com.techys.classification.screen

//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
){
    if (image?.uri == null)
        return
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        DownsampledImage(
            uri = image.uri,
            modifier = Modifier.fillMaxSize()
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
