from fastapi import FastAPI, UploadFile, File
import inference.processing
import logging

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
    result = inference.processing.process_image(contents)
    return {"result": result, "filename": file.filename}
