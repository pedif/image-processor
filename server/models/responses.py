from pydantic import BaseModel, Field


class ImageResponse(BaseModel):
    label: str = Field(..., description="Predicted class label")
    confidence: float = Field(
        ..., description="Prediction confidence (0.00-1.00)", example=0.87
    )
