import requests

# URL of local FastAPI endpoint
url = "http://127.0.0.1:8000/upload"

# Path to test image
image_path = "uploads/test.png"

# Open the file in binary mode and send POST request
with open(image_path, "rb") as f:
    files = {"file": f}
    response = requests.post(url, files=files)

# Print the JSON response
print(response.status_code)
print(response.json())