# Bigio Internship Test

## Introduction :

A mobile application developed in Kotlin using Jetpack Compose as part of the Bigio internship test. The app demonstrates API integration, Room database caching, and navigation within a clean and modern Android architecture.

## Table of Contents

- [Introduction](#introduction-)
- [Features](#features-)
- [Libraries](#libraries-)
- [Project Structure](#project-structure-)
- [APK Link](#apk-link-)

## Features :

- Display a list of characters from API
- View character details
- Save and unsave favorite characters
- Search character by name
- Offline caching using Room
- Image loading with Coil
- Built with declarative UI (Jetpack Compose)

## Libraries :

- **Jetpack Compose** – Modern UI toolkit for building native Android UI
- **Retrofit** – Networking library for API communication
- **Gson Converter** – JSON serialization/deserialization
- **OkHttp Logging Interceptor** – HTTP logging
- **Room** – Local database for offline data persistence
- **Coil** – Image loading library optimized for Compose
- **Navigation Compose** – Navigation graph for Compose UI
- **Material 3** – UI component design system
- **ViewModel + Lifecycle** – Architecture component for state management

## Project Structure :

- `data/` – Contains data layer logic including:
  - `local/` – Room database, entities, and DAO
  - `models/` – Retrofit API response data classes (DTOs)
  - `repository/` – Repository implementation that manages data flow
- `di/` – Dependency Injection setup
- `ui/` – UI layer with:
  - `screens/` – List, detail, and favorite screens
  - `theme/` – Theming and styling definitions
  - `navigation/` – Navigation graph setup using Navigation Compose
- `services/` – Retrofit API interface defining all network endpoints

## APK Link :

[Download APK (Google Drive)](https://drive.google.com/drive/folders/1hQXbv7G3y26ZQ67hPHx5kdDYxSV0C5ZP?usp=drive_link)
