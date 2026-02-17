from pydantic import BaseModel, Field


class ImageResponse(BaseModel):
    label: str
    confidence: float
    model_config = {
        "json_schema_extra": {
            "examples": [{"label": "golden retriever", "confidence": 0.95}]
        }
    }
