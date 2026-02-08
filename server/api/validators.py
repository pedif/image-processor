from fastapi import HTTPException
from io import BytesIO
from PIL import Image


# We don't want to accept big files in case they crash our server
MAX_FILE_SIZE = 5 * 1024 * 1024  # 5MB


# File Size Check
def check_file_size(contents: bytes):
    if len(contents) > MAX_FILE_SIZE:
        raise HTTPException(status_code=413, detail="File too large")


# Image Validation
def check_image_validity(contents: bytes):
    try:
        image = Image.open(BytesIO(contents))
        # Validate image integrity
        image.verify()
    except Exception:
        raise HTTPException(status_code=400, detail="Invalid image file")
