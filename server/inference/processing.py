from PIL import Image
import io


def process_image(file_bytes: bytes):
    try:
        image = Image.open(io.BytesIO(file_bytes))
        # for now we just return the image size
        width, height = image.size
        return {"width": width, "height": height}
    except Exception as e:
        return {"error": str(e)}
