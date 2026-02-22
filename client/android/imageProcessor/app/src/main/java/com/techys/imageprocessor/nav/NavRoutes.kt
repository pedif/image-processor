package com.techys.imageprocessor.nav

import kotlinx.serialization.Serializable

@Serializable
data class ClassifyRoute(val imagePath: String?)

@Serializable
object ImageCaptureRoute