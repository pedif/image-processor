from fastapi.testclient import TestClient
from api.main import app

client = TestClient(app)

# URL of local FastAPI endpoint
url = "http://127.0.0.1:8000/upload"

# Path to test image
image_path = "uploads/test.png"
image_name = "test.png"

def test_image_upload():
    with open(image_path, "rb") as f:
        response = client.post(
            "upload/",
            files = {"file": f}
        )
        
        assert response.status_code == 200
        data = response.json()
        assert "label" in data
        assert "confidence" in data
        assert 0.0 <= data["confidence"] <= 1.0

