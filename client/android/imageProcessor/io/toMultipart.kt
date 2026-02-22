fun File.toMultipart(): MultipartBody.Part {
    val requestFile = this.asRequestBody("image/*".toMediaType())
    return MultipartBody.Part.createFormData(
        name = "image",   // must match backend field name
        filename = this.name,
        body = requestFile
    )
}