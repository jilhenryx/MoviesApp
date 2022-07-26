# MoviesApp (Under Construction :hammer_and_pick: :construction_worker_man: :hammer_and_pick:)
A TV Shows and Movies Listing App built entirely with Jetpack Compose. It is inpired by Google's Search ***What to Watch*** and aimed at providing users with the ability to keep track of shows they have watched, shows they want to watch as well as keep abreast with new releases.
This project will use all recent Modern Android Developer practices, libraries and technologies.

## Table of Contents
- [Current Progress](https://github.com/jilhenryx/MoviesApp#current-progress)
- [Installing MoviesApp](https://github.com/jilhenryx/MoviesApp#installing-moviesapp)
- [Current Technical Details](https://github.com/jilhenryx/MoviesApp#current-technical-details)
- [Project Samples](https://github.com/jilhenryx/MoviesApp#project-samples)
- [Contributions and Feedback](https://github.com/jilhenryx/MoviesApp#contributions-and-feedback)


## Current Progress
- [x] Authentication
- [ ] Movies/TV Shows List
- [ ] Movies/TV SHows Details
- [ ] Watched List
- [ ] Yet to Watch List
- [ ] Favourite List
- [ ] Cloud Integration

## Installing MoviesApp
- **Minimum SDK:** 23 and **Target SDK:** 32
- MoviesApp uses **Kotlin 1.7.0** and was built with **Android Studio Chipmunk**. Ensure your dependencies are updated to avoid errors.
- MoviesApp uses Firebase for cloud functionalities. Please setup up a Firebase project using this [guide](https://firebase.google.com/docs/android/setup). You basically need the `google-services.json` file.
- To use the Google's One-Tap Functionality, set of a Google Console Project using this [guide](https://developers.google.com/identity/one-tap/android/get-started) and add your Web Application ClientID generated from your console project in [`gradle.properties`](https://github.com/jilhenryx/MoviesApp/blob/authentication/gradle.properties) replacing the empty `oneTapClientID` value.
*Note this is your server's client ID, not your Android client ID.*

[Back Up](https://github.com/jilhenryx/MoviesApp#table-of-contents) :point_up_2:

## Current Technical Details
- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** Clean Architecture (MVVM)
- **DI:** Hilt
- **Third Party Libraries:** Google's Accompanist Library for Animated Navigation, Firebase and Google Play Services for Authentication
- **Kotlin Components:** Kotlin Coroutines and Flow

[Back Up](https://github.com/jilhenryx/MoviesApp#table-of-contents) :point_up_2:

## Project Samples
### Create Account Authentication Flow
![Account Creation Flow](/gitmedia/MoviesApp-Create-Flow.gif)

[Back Up](https://github.com/jilhenryx/MoviesApp#table-of-contents) :point_up_2:

### Confirm Email Authentication Flow
![Confrim Email Flow](/gitmedia/MoviesApp-Confirm-Email-Flow.gif)

[Back Up](https://github.com/jilhenryx/MoviesApp#table-of-contents) :point_up_2:

### Forgot Password Authentication Flow
![Forgot Password Flow](/gitmedia/MoviesApp-Forgot-Password-Flow.gif)

[Back Up](https://github.com/jilhenryx/MoviesApp#table-of-contents) :point_up_2:

### Google One-Tap Authentication Flow
![Google One-Tap Flow](/gitmedia/MoviesApp-Google-Login-Flow.gif)

[Back Up](https://github.com/jilhenryx/MoviesApp#table-of-contents) :point_up_2:

## Contributions and Feedback
I am very open to feedback and contributions on the work done so far. My aim for working on this project is to create a real world application. Please raise an issue or leave a comment. Thank you

[Back Up](https://github.com/jilhenryx/MoviesApp#table-of-contents) :point_up_2:
