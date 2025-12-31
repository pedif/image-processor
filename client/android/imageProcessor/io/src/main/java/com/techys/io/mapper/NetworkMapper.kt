package com.techys.io.mapper

import com.techys.io.network.model.ImageLabelDto
import com.techys.ip.domain.model.ImageLabel

fun ImageLabelDto.toDomain(): ImageLabel {
    return ImageLabel(label = label, confidence = confidence)
}