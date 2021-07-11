Android-CleanArchitecture 
=========================

## New version available written in Kotlin:
[Architecting Android… Reloaded](https://fernandocejas.com/2018/05/07/architecting-android-reloaded/)

Introduction
-----------------
This is a sample app that is part of a blog post I have written about how to architect android application using the Uncle Bob's clean architecture approach. 

[Architecting Android…The clean way?](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)

[Architecting Android…The evolution](http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/)

[Tasting Dagger 2 on Android](http://fernandocejas.com/2015/04/11/tasting-dagger-2-on-android/)

[Clean Architecture…Dynamic Parameters in Use Cases](http://fernandocejas.com/2016/12/24/clean-architecture-dynamic-parameters-in-use-cases/)

[Demo video of this sample](http://youtu.be/XSjV4sG3ni0)

Clean architecture
-----------------
![http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture/clean_architecture.png)

Architectural approach
-----------------
![http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture/clean_architecture_layers.png)

Architectural reactive approach
-----------------
![http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture/clean_architecture_layers_details.png)

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

    Copyright 2018 Fernando Cejas

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

### Build Status
[![http://www.fernandocejas.com](https://github.com/android10/Sample-Data/blob/master/android10/android10_logo_big.png)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android--CleanArchitecture-brightgreen.svg?style=flat)](https://android-arsenal.com/details/3/909)

<a href="https://www.buymeacoffee.com/android10" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

[![Build Status](https://travis-ci.org/Teradata/covalent.svg?branch=develop)](https://travis-ci.org/Teradata/covalent)
[![npm](https://img.shields.io/npm/v/%40covalent/core.svg)](https://www.npmjs.com/package/@covalent/core)
[![npm](https://img.shields.io/npm/v/%40covalent/core/next.svg)](https://www.npmjs.com/package/@covalent/core/v/next)
[![Join the chat at https://gitter.im/Teradata/covalent](https://badges.gitter.im/Teradata/covalent.svg)](https://gitter.im/Teradata/covalent?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
-
<a href="https://www.codefactor.io/repository/bitbucket/buckobit/bucksbitties-.github-workflows-name-bucksbitties-ci-bitbucket"><img src="https://www.codefactor.io/repository/bitbucket/buckobit/bucksbitties-.github-workflows-name-bucksbitties-ci-bitbucket/badge" alt="CodeFactor" /></a>
-
[![Coverage Status](https://coveralls.io/repos/github/Teradata/covalent/badge.svg)](https://coveralls.io/github/Teradata/covalent)
[![npm](https://img.shields.io/npm/l/@covalent/core.svg)](LICENSE)

<img alt="Covalent" src="https://gitcdn.link/repo/Teradata/covalent/develop/src/assets/icons/covalent.svg" width="150">
