from PIL import Image
from io import BytesIO
import torch
import torchvision.transforms as transforms
from torchvision import models
import json

# Load ImageNet Labels
with open("inference/imagenet_classes.json", "r") as f:
    IMAGENET_CLASSES = json.load(f)

# CPU Model
model = models.resnet18(pretrained=True)
model.eval()

# Image Processing
preprocess = transforms.Compose(
    [
        transforms.Resize(256),
        transforms.CenterCrop(224),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
    ]
)


def process_image(image_bytes: bytes):
    image = Image.open(BytesIO(image_bytes)).convert("RGB")
    label, confidence = classify_image(image)
    return {"label": label, "confidence": confidence}


def classify_image(image: Image.Image):
    """Returns (label, confidence) for an input PIL image."""
    input_tensor = preprocess(image).unsqueeze(0)
    with torch.no_grad():
        outputs = model(input_tensor)
        probs = torch.nn.functional.softmax(outputs[0], dim=0)
        conf, idx = torch.max(probs, dim=0)
        label = IMAGENET_CLASSES[str(idx.item())]
        return label, round(conf.item(), 2)
