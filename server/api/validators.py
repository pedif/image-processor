from fastapi import HTTPException
from io import BytesIO
from PIL import Image
from models.errors import ErrorResponse, ErrorCode


# We don't want to accept big files in case they crash our server
MAX_FILE_SIZE = 5 * 1024 * 1024  # 5MB


# File Size Check
def check_file_size(contents: bytes):
    if len(contents) > MAX_FILE_SIZE:
        raise HTTPException(
            status_code=413,
            detail=ErrorResponse(
                error_code=ErrorCode.FILE_TOO_LARGE, message="Max file size is 5MB"
            ),
        )


# Image Validation
def check_image_validity(contents: bytes):
    try:
        image = Image.open(BytesIO(contents))
        # Validate image integrity
        image.verify()
    except Exception:
        raise HTTPException(
            status_code=400,
            detail=ErrorResponse(
                error_code=ErrorCode.INVALID_IMAGE_FILE, message="Not an image file"
            ),
        )
