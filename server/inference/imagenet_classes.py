import json
from torchvision import models
import pathlib

# Using default ImageNet class labels
imagenet_classes = models.ResNet18_Weights.DEFAULT.meta["categories"]

# Convert to dictionary with string keys
imagenet_dict = {str(i): label for i, label in enumerate(imagenet_classes)}

BASE_DIR = pathlib.Path(__file__).parent

# Saving to JSON
with open(BASE_DIR / "imagenet_classes.json", "w") as f:
    json.dump(imagenet_dict, f, indent=2)

print("imagenet_classes.json generated successfully!")
