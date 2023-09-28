# AppGenerator

A JavaFX-based desktop application for generating customized Android projects with ease.

## Table of Contents

- [Description](#description)
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)

## Description

The Android Project Generator is a user-friendly tool that simplifies the process of creating Android projects with specific configurations. It provides a graphical user interface (GUI) for users to input project details, such as the app name, button color, logo, and upload the `google-services.json` file. The application then generates a customized Android project with these configurations, making it ideal for developers looking to streamline project setup.

## Features

- Easy-to-use GUI for project configuration.
- Customization options for app name, button color, and logo.
- Supports uploading the `google-services.json` file for Firebase integration.
- Generates a structured Android project with the specified configurations.

## Getting Started

To get started with the Android Project Generator, follow these steps:

1. Clone this repository to your local machine.
2. Open the project in your preferred Java development environment.
3. Build the project and run the `AndroidProjectGeneratorGUI` class.

## Usage

1. Launch the application.
2. Fill in the app name, select a button color, and upload a logo.
3. Upload your `google-services.json` file (required for Firebase integration).
4. Click the "Generate Project" button.
5. The application will create a customized Android project in the specified directory.
---
## Work Log

### Weeks 1-2

**Summary:**
During the initial two weeks of the project, I embarked on creating a tool to generate Android APKs. The purpose of this tool was to simplify and automate the process of generating Android projects with specific configurations.

**Approaches Tried:**
1. **Manual APK Generation:** In the first week, I explored the possibility of manually generating APK files by following Android Studio's standard procedures. However, this approach proved impractical, time-consuming, and error-prone, particularly when customization was required.

2. **Dynamic Google-service.json Generation:** I attempted to create the `google-services.json` file dynamically during project generation. Unfortunately, this approach encountered significant challenges. It required deep integration with Firebase and Google Cloud services, making it overly complex and prone to potential security and authentication issues. Additionally, dynamic generation was not a recommended practice for production scenarios due to security concerns.

**Chosen Approach:**
After assessing the drawbacks of the previous approaches, I decided to create a JavaFX-based desktop application called the "Android Project Generator." This application offers a user-friendly GUI that allows users to input project details such as the app name, button color, logo, and upload the `google-services.json` file. The app then generates a customized Android project with these configurations.

**Current Approach Implemented:**
The implemented approach involves using JavaFX for the user interface, Apache Commons IO for file operations, and standard Android project templates for generating the Android project structure. This approach strikes a balance between ease of use and customization while maintaining security and stability.

**Estimated Hours Spent :** 21 hours.

### Weeks 3-4

**Summary:**
established a fundamental framework for an appointment booking feature within an Android application. 
It encompasses the creation of an activity with a user-friendly interface, featuring a calendar for date selection, a dynamic list of available appointments, and a booking functionality initiated by user interaction. 
Users can easily browse, select, and confirm appointments, with booked slots being dynamically updated and reflected in the user interface. This code serves as a foundational structure, facilitating the integration of more advanced functionalities and data management systems, such as Firebase, to enable the complete operation of a robust appointment booking system.
moreover, a change was done in the UI to enable the user to pick which project template he is interested in coding.

**Estimated Hours Spent :** 11 hours.

