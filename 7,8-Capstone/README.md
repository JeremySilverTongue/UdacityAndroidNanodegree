# 42 Winks





# Rubric items

## Core Platform Development

### App integrates a third-party library.

Using Timber for logging and ButterKnife for view/resource injection.

### App validates all input from servers and users. If data does not exist or is in the wrong format, the app logs this fact and does not crash.

Doesn't talk with any servers, and the only input from users is hitting a button.

### App includes support for accessibility. That includes content descriptions, navigation using a D-pad, and, if applicable, non-audio versions of audio cues.

// TODO: Check this

### App keeps all strings in a strings.xml file and enables RTL layout switching on all layouts.

// TODO: Check this

### App provides a widget to provide relevant information to the user on the home screen.

Widget allows the user to log sleep/wake up, and displays the last night's sleep

## Google Play Services

### App integrates two or more Google services. Google service integrations can be a part of Google Play Services or Firebase.

Using Firebase analytics and ads.

### Each service imported in the build.gradle is used in the app.

    compile 'com.google.firebase:firebase-ads:9.2.1'
    compile 'com.google.firebase:firebase-core:9.2.1'

