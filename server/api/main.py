from fastapi import FastAPI, UploadFile, File, HTTPException
import inference.processing
import logging
import asyncio
from api.validators import check_file_size, check_image_validity
from models.responses import ImageResponse
from models.errors import ErrorResponse, ErrorCode


logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI(
    title="Image Processor API",
    description="A FastAPI server to classify images.",
    version="1.0",
)


@app.get("/")
async def home():
    return {"message": "Hello, ImageProcessor!"}


@app.get("/health", tags=["Health Check"])
async def health():
    return {"status": "ok"}


@app.post(
    "/classify",
    response_model=ImageResponse,
    summary="Upload an image for classification",
    description="Upload an image file (JPEG or PNG). The server will return the predicted label and confidence score.",
    tags=["Image Processing"],
    responses={
        400: {"model": ErrorResponse, "description": "Invalid image"},
        413: {"model": ErrorResponse, "description": "File too large"},
        500: {"model": ErrorResponse, "description": "Internal server error"},
    },
)
async def classify_image(
    file: UploadFile = File(..., description="The image file to classify (max 5MB)")
):
    contents = await file.read()
    logger.info(f"Received file: {file.filename}")

    # Checking the validity of the uploaded file before starting the processing
    check_file_size(contents)
    check_image_validity(contents)

    loop = asyncio.get_running_loop()
    try:
        result = await loop.run_in_executor(
            None, inference.processing.process_image, contents
        )
    except Exception as e:
        # Raise an HTTPException with a 400 status code for bad input (invalid image)
        raise HTTPException(
            status_code=400,
            detail=ErrorResponse(
                error_code=ErrorCode.PROCESSING_CRASH,
                message=f"Error processing image: {str(e)}",
            ),
        )

    return result
