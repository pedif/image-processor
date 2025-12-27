# Image Processor (Android + AI)

A portfolio project exploring the intersection of **mobile developement** and **applied AI**.

This repository contains a full-stack setup:
- **Android client* for image capture and upload
- **Python backend** for image classification
- Future support for **ondevice inference** (TFLite)

This project is build incrementally.


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

image-processor/           ← Root
├── client/                ← Android client code
│   └── android/           ← Android Studio project
│       ├── app/           ← App module
│       ├── core/          ← Shared logic, utilities, network
│       ├── feature-*      ← Feature modules (classifier, camera, etc.)
│       ├── domain/           ← Business logic
│       ├── io/            ← Networking and local io operations
│       ├── ml/            ← Local(offline) ml processing
│       └── build-logic/   ← Gradle convention plugins & version catalog
├── server/                ← Python backend
│   └── api/
│       ├── app/           ← FastAPI application
│       ├── inference/     ← Model loading & inference scripts
│       ├── models/        ← Model files (.pth, .onnx, later .tflite)
│       └── tests/         ← Backend unit tests
├── docs/                  ← Documentation, diagrams, design notes
├── .github/               ← GitHub Actions workflows
└── README.md              ← Root README


---


## Project Goals
-Build a production-style Android app that captures and upload imageds
- Implement a Python-based inference backend
- Bridge backend AI models with mobile deployment
- Main clean Git history and incremental improvements


---


## Developement Approach
- Features are added progressively
- CI/CD will be introduced 
-- Emphasis on learning, clarity, and maintainability over shortcuts


---


## Status

U+1F6A7 Work in progress
This project is under active developement.