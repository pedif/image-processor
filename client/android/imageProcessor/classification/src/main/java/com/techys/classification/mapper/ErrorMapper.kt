package com.techys.classification.mapper

import com.techys.classification.R
import com.techys.ip.domain.model.ClassificationError

/**
 * Mapper from a domain error to a readable
 * message which will be displayed to the user
 * @return @StringRes
 */
fun ClassificationError.getMessage(): Int {
    return when (this) {
        ClassificationError.FileNotFound -> R.string.error_classification_file_not_found
        ClassificationError.Timeout -> R.string.error_classification_timeout
        ClassificationError.ProcessingCrash -> R.string.error_classification_crash
        ClassificationError.Network -> R.string.error_classification_network
        is ClassificationError.UnknownServerError -> {
            R.string.error_classification_server_crash
        }

        ClassificationError.InvalidImageFormat, ClassificationError.FileTooLarge -> {
            R.string.error_classification_image_invalid
        }

        is ClassificationError.Unknown -> {
            R.string.error_classification_unknown
        }
    }
}
