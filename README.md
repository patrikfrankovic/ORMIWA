# ORMIWA — Web & Android Development Coursework

A collection of laboratory exercises and smaller projects from a web and mobile application development course. It starts with HTML/CSS assignments and progresses to Android development using Kotlin.

The **Memories** Android photo application has been separated into its own repository: [Memories](https://github.com/patrikfrankovic/Memories).

## Technologies

- **Web:** HTML, CSS, responsive layout exercises and static pages
- **Android:** Kotlin, Android Studio, XML layouts, Gradle
- **Networking/data:** Android networking examples and Firebase-backed exercises

## Contents

| Directory | Contents |
| --- | --- |
| [`LV1/`](LV1/) | Introductory HTML pages |
| [`LV2/`](LV2/) | HTML elements, links, image maps, and page layouts |
| [`LV3/`](LV3/) | HTML/CSS pages and visual styling |
| [`LV4/`](LV4/) | CSS layout exercises and a packaged web assignment |
| [`LV5/`](LV5/) | Introductory Android/Kotlin project |
| [`LV6/`](LV6/) | Android/Kotlin laboratory apps |
| [`LV7/`](LV7/) | Android networking and API examples |
| [`LV8/`](LV8/) | Android app using Firebase/Firestore |
| [`Portfolio/`](Portfolio/) | Static HTML/CSS personal portfolio website |

Each Android lab is a **separate Gradle project**. Open the relevant `zad` or `primjer` directory in Android Studio rather than opening the entire repository as one Android app.

## Running the projects

**Web pages:** Open a lab's `index.html` in a web browser. Some pages reference internet-hosted images or other external resources.

**Android labs:** Use Android Studio, open the appropriate project directory, and let Gradle sync. The archived projects use historical Android Gradle Plugin and SDK versions, so recent IDE/SDK combinations may require upgrades.

For Firebase-based labs, register an application with Firebase and supply the appropriate `app/google-services.json` file locally. Firebase configuration and `local.properties` have been deliberately excluded from the public package.

## Project separation

The standalone Memories application is **not duplicated here**. Its original source was relocated into a dedicated public-ready package without altering Kotlin application logic.

## Scope

This repository is an **academic exercise archive**. Individual projects are independent, and no claim is made that all builds run unchanged on current Android tooling. Third-party course handouts, local machine configurations, and generated artifacts are not included in this cleaned package.
