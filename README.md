Android-CleanArchitecture
=========================

This is a sample app that is part of a blog post I have written about how to architect android application using the Uncle Bob's clean architecture approach. 

[Here is the blog post](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)

[Here is a demo video](http://youtu.be/XSjV4sG3ni0)


Local Development
-----------------

Here are some useful Gradle/adb commands for executing this example:

 * `gradlew clean build` - Build the entire example and execute unit and integration tests plus lint check.
 * `gradlew installDebug` - Install the debug apk in the current connected device.
 * `gradlew test` - Execute domain and data layer tests (both unit and integration)
 * `gradlew connectedAndroidTest` - Execute espresso and instrumentation function tests.


License
--------

    Copyright 2014 Fernando Cejas

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
