from api.validators import check_file_size, check_image_validity
import pytest
from fastapi import HTTPException

# path to test image
image_path = "uploads/test.png"

def test_check_file_size_valid_file():
    with open(image_path, "rb") as f:
        contents = f.read()
        # Test valid size
        check_file_size(contents)


def test_check_file_size_large_file():
        # Test valid size
        contents= b"A" * (5 * 1024 * 1024 + 1) #5MB +1
        with pytest.raises(HTTPException):
            check_file_size(contents)



def test_check_image_validity_valid_file():
    with open(image_path, "rb") as f:
        contents = f.read()
        # Test valid size
        check_image_validity(contents)



def test_check_image_validity_invalid_file():
    with open(image_path, "rb") as f:
        contents = f.read()
        contents = b"not_image_file"
        with pytest.raises(HTTPException):
            check_image_validity(contents)
