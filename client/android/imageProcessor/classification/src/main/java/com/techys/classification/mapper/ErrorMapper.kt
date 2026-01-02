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
        ClassificationError.ModelCrashed -> R.string.error_classification_crash
        else ->
            R.string.error_classification_unknown
    }
}
