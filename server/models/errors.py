from enum import Enum
from pydantic import BaseModel


class ErrorCode(str, Enum):
    INVALID_IMAGE_FILE = "INVALID_IMAGE_FILE"
    FILE_TOO_LARGE = "FILE_TOO_LARGE"
    PROCESSING_CRASH = "PROCESSING_CRASH"


class ErrorResponse(BaseModel):
    error_code: ErrorCode
    message: str
