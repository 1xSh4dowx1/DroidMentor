# DroidMentor

DroidMentor is an Android application that works as a personal **Android development mentor**, using Google's Gemini API to answer questions and provide guidance about Android development.

The application follows a **Bring Your Own Key (BYOK)** approach, allowing users to configure their own Gemini API key.

## Features

* Chat with an Android development mentor
* Save and access previous conversations
* Edit messages and rewrite conversations
* Delete conversations
* Offline access to saved conversations
* Gemini API integration
* Secure storage of the API key
* Error handling for network/API errors
* About and Settings screens

## Screens

* **Title** – Main menu
* **Chat History** – List of previous conversations
* **Active Chat** – Conversation with the mentor
* **About** – Application and authors information
* **Settings** – Gemini API key configuration

## Technologies

* Kotlin
* Android
* Jetpack Compose
* Ktor Client
* Kotlinx Serialization
* Room
* DataStore
* Gemini REST API

## Architecture

The application is designed as an **offline-first** Android application. Conversation history is stored locally using Room, while the Gemini API is used for new interactions when an internet connection is available.

The application manages the complete conversation history on the client side when communicating with Gemini.

## Authors

* **Duarte Rodrigues 52599**
* **Isadora Mendes 52559**
* **Daniel Viegas 52885**

## Project Plan

The project plan, with tasks, milestones and assignees, is available in [docs/PROJECT_PLAN.md](docs/projectPlan.md).

This document was drafted with the assistance of Claude (Anthropic) and is meant only as an initial guideline. It may be adjusted throughout the project as the group's needs and progress evolve.

## Course

**Mobile Devices Programming**  
Instituto Superior de Engenharia de Lisboa (ISEL)  
Academic Year 2026/2027