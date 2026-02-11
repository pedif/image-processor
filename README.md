# Image Processor (Android + AI)

A portfolio project exploring the intersection of **mobile development** and **applied AI**.

This repository contains a full-stack setup:
- **Android client** for image capture and upload
- **Python backend** for image classification
- Future support for **ondevice inference** (TFLite)

This project is built incrementally.


---


## Tech Stack

### Client (Android)
- Kotlin
- CameraX
- Retrofit
- Hilt
- Multi-module architecture
- Gradle Version Catalog

### Server (Python)
- FastAPI
- PyTorch (training * inference)
- Torch -> TFLite export (later stage)


---


## Repository Structure
```
ImageProcessor/            ← Root
├── client/
│   └── android/imageProcessor/   ← Android project (Gradle + Kotlin)
│       ├── app/                  ← Application entry, DI wiring
│       ├── classification/       ← Classification UI & ViewModel
│       ├── core/                  ← Shared DI, UiState, logging
│       ├── common/                ← Logger interface, test utils
│       ├── designSystem/          ← Theme, colors, typography
│       ├── domain/                ← Use cases, repository interface, models
│       ├── io/                    ← Network DTOs, mappers
│       └── ml/                    ← Local ML (TFLite later)
├── server/                 ← Python backend (FastAPI)
│   ├── api/                ← FastAPI app, routes, validators
│   ├── inference/          ← Model loading, ImageNet classes, processing
│   ├── models/             ← Model files (.pth, .onnx, later .tflite)
│   ├── tests/              ← API and validator tests
│   ├── uploads/            ← Uploaded images (dev)
│   ├── pyproject.toml
│   └── requirements.txt
└── README.md
```

---


## Project Goals
- Build a production-style Android app that captures and uploads images
- Implement a Python-based inference backend
- Bridge backend AI models with mobile deployment
- Maintain clean Git history and incremental improvements


---


## Development Approach
- Features are added progressively
- CI/CD will be introduced later
- Emphasis on learning, clarity, and maintainability over shortcuts


---


## Status

🚧 Work in progress — this project is under active development.