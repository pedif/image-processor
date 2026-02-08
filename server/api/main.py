from fastapi import FastAPI, UploadFile, File
import inference.processing
import logging
import asyncio

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI(title="ImageProcessor API")


@app.get("/")
def home():
    return {"message": "Hello, ImageProcessor!"}


@app.post("/upload")
async def upload_image(file: UploadFile = File(...)):
    contents = await file.read()
    logger.info(f"Received file: {file.filename}")
    loop = asyncio.get_running_loop()
    result = await loop.run_in_executor(None,inference.processing.process_image, contents )
    return result
