package com.techys.ip.domain.model

data class ImageLabel(
    //we don't need id for now since we don't have a local storage yet
//    val id: Int,
    val label: String,
    val confidence: Float
)
