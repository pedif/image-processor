import requests
import pathlib

PARENT_DIR = pathlib.Path(__file__).parent.parent

# URL of local FastAPI endpoint
url = "http://127.0.0.1:8000/classify"


# Path to test image
image_path = PARENT_DIR / "uploads/test.png"

# Open the file in binary mode and send POST request
with open(image_path, "rb") as f:
    files = {"file": f}
    response = requests.post(url, files=files)

# Print the JSON response
try:
    print(response.status_code)
    print(response.json())
except Exception as e:
    print("error:", e)
