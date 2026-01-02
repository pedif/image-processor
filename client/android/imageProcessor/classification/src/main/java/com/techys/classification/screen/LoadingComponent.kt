package com.techys.classification.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.techys.ip.designsystem.theme.Color
import com.techys.ip.designsystem.theme.Dimen

@Composable
fun LoadingComponent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.greySemiTransparent)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(Dimen.paddingHuge)
                .align(Alignment.Center)
        )
    }
}
