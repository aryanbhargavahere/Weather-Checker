# ☁️ WeatherChecker

WeatherChecker is an Android application that displays **real-time weather information** using the **OpenWeatherMap API**.  
The app fetches current weather data based on the user’s location and presents it in a clean, modern UI.

---

## 📑 Table of Contents
1. 📝 [Description](#-description)
2. ✨ [Features](#-features)
3. 🛠️ [Tech Stack](#-tech-stack)
4. 📂 [Project Structure](#-project-structure)
5. 🌐 [API Used](#-api-used)
6. 🚀 [Getting Started](#-getting-started)
    - 📋 [Prerequisites](#prerequisites)
    - ▶️ [Steps to Run](#steps-to-run)
7. 🧠 [How the App Works](#-how-the-app-works)
8. 🔮 [Future Enhancements](#-future-enhancements)
9. 👤 [Author](#author)

---

## 📖 Description

WeatherChecker is built using **Kotlin** and modern Android development practices.  
It uses **location services** and a **REST API** to fetch live weather data such as temperature, weather condition, wind speed, humidity, and coordinates.

The app also checks for **network availability** before making API calls to ensure smooth performance.

---

## ✨ Features

- 🌍 Fetches weather using current device location  
- 🌡️ Displays temperature in metric units  
- ☁️ Shows weather condition (Clear, Clouds, Rain, etc.)  
- 💨 Wind speed and cloud percentage  
- 📍 Latitude & longitude details  
- 🌐 Network availability check  
- 📱 Simple and responsive UI  

---

## 🛠️ Tech Stack

- **Kotlin**
- **Android SDK**
- **XML Layouts**
- **Retrofit**
- **Gson**
- **REST API**

---

## 📂 Project Structure

```bash
app/
 └── src/main/java/com/example/weatherchecker/
     ├── MainActivity.kt              # Main UI and logic
     ├── WeatherServiceapi.kt         # Retrofit API interface
     ├── utild/
     │   └── Constants.kt             # API constants & network check
     ├── model/
     │   ├── WeatherResponse.kt
     │   ├── Weather.kt
     │   ├── Wind.kt
     │   ├── Clouds.kt
     │   ├── Coord.kt
     │   └── Sys.kt
 └── src/main/res/
     ├── layout/
     │   └── activity_main.xml
     ├── drawable/
     │   └── bg_info_card.xml
     └── values/
```
---
## 🌐 API Used

- Free Weather API (REST-based)
- Data fetched in **JSON** format
- Integrated using **Retrofit**

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (latest version recommended)
- **Android SDK 24** or above
- **Internet connection**

### Steps to Run
1. Download or clone the repository
2. Open the project in **Android Studio**
3. Add your **Weather API key**
4. Sync **Gradle**
5. Run the app on an **emulator** or **physical device**

---
## 🧠 How the App Works

1. User enters a **city name** or uses **location services**
2. App sends a request to the **weather API**
3. API responds with **JSON data**
4. Data is **parsed** and displayed on the **UI**

---
## 🔮 Future Enhancements

- 📅 **7-day weather forecast**
- 🌙 **Dark mode**
- 📍 **Auto-detect current location**
- 🌬️ **Wind speed & pressure details**
- 🗺️ **Map-based weather view**
---
## Author
- **Name:** Aryan
---
