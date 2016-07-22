Android-CleanArchitecture [![Build Status](https://travis-ci.org/android10/Android-CleanArchitecture.svg?branch=master)](https://travis-ci.org/android10/Android-CleanArchitecture)
=========================

This is a sample app that is part of a blog post I have written about how to architect android application using the Uncle Bob's clean architecture approach. 

[Architecting Android…The clean way?](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)

[Architecting Android…The evolution](http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/)

[Tasting Dagger 2 on Android](http://fernandocejas.com/2015/04/11/tasting-dagger-2-on-android/)

[Demo video of this sample](http://youtu.be/XSjV4sG3ni0)

Clean architecture
-----------------
![http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/](http://fernandocejas.com/wp-content/uploads/2014/09/clean_architecture1.png)

Architectural approach
-----------------
![http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/](http://fernandocejas.com/wp-content/uploads/2014/09/clean_architecture_android.png)

Architectural reactive approach
-----------------
![http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/](http://fernandocejas.com/wp-content/uploads/2015/07/clean_architecture_evolution.png)

Local Development
-----------------

Here are some useful Gradle/adb commands for executing this example:

 * `./gradlew clean build` - Build the entire example and execute unit and integration tests plus lint check.
 * `./gradlew installDebug` - Install the debug apk on the current connected device.
 * `./gradlew runUnitTests` - Execute domain and data layer tests (both unit and integration).
 * `./gradlew runAcceptanceTests` - Execute espresso and instrumentation acceptance tests.
 
Discussions
-----------------

Refer to the issues section: https://github.com/android10/Android-CleanArchitecture/issues
 

Code style
-----------

Here you can download and install the java codestyle.
https://github.com/android10/java-code-styles


License
--------

    Copyright 2016 Fernando Cejas

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


![http://www.fernandocejas.com](http://www.android10.org/myimages/android10_logo_big_github.png)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android--CleanArchitecture-brightgreen.svg?style=flat)](https://android-arsenal.com/details/3/909)
