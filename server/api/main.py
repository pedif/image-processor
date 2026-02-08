from fastapi import FastAPI, UploadFile, File, HTTPException
import inference.processing
import logging
import asyncio
from api.validators import check_file_size, check_image_validity

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI(title="ImageProcessor API")

@app.get("/")
async def home():
    return {"message": "Hello, ImageProcessor!"}


@app.get("/health")
async def health():
    return {"status": "ok"}


@app.post("/upload")
async def upload_image(file: UploadFile = File(...)):
    contents = await file.read()
    logger.info(f"Received file: {file.filename}")

    # Checking the validity of the uploaded file before starting the processing
    check_file_size(contents)
    check_image_validity(contents)

    loop = asyncio.get_running_loop()
    result = await loop.run_in_executor(
        None, inference.processing.process_image, contents
    )
    return result
