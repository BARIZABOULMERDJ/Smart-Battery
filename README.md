# Smart-Battery

## Scénario d’utilisation : 

Bob possède 50 % de batterie et souhaite se rendre dans quelques heures à la médiathèque d’Antibes, lieu où il a l’habitude de regarder des vidéos YouTube sur son smartphone.
Bob consulte sa carte afin de voir la consommation habituelle de batterie de cet emplacement.
Il remarque que cet emplacement fait partie du cluster « consommation forte ».
En cliquant dessus l’application lui indique qu’il utilise habituellement 54 % de batterie pour une luminosité moyenne de 30 %.
Ainsi il pourra mettre son téléphone à charger ou baisser sa luminosité pour économiser sa batterie.
Il pourra également étiqueter ses lieux fréquents, afin de trouver plus facilement l’emplacement.


## Getting Started

Before you start to download and see the code, please take a look of **Smart-Battery/Sketch/Desgin/result_portait** on the branch Design. In these repository, there are portaits of the structure of this app.

This app has 3 modules : **Authentification**, **Localisation/GPS** and **Capteurs information of real device**.
![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/design/Sketch/Desgin/result%20portait/total.png)

Then you can download all the files on the branch androidApp and open them as an android app project in Android Studio.

### Prerequisites

This app use Google Firebase as the back-end server. 

- The Authentification module use the sign_in_with_email_and_password service of Firebase. So if you want to use the same service in your own app, you should first read the document of Firebase, then create your own project on your firebase console, enable this service in your project, and then conect it with your android studio.

- The localisation/GPS module dosen't use Firebase but the Google Maps API directly.

- The capteurs information use the realtime database service of Firebase to restore these information under the userId of the current user signed in. The realtime database can rewrite the same data stored. To use this service, your should finish the Authentification module, then you'll know how to use this service in your android studio.


After following the document of Google Firebase and Google App Engine (for using API), in android studio app level build.gradle, we can see the dependencies like this:  
![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/tutorial/build_gradle_app_dependencies.png)

### The reason to use Firebase

It's very easy to understand Firebase. The document is very simple and clear. What is most important is that the code is extremely simple and easy, but can realise these powerful functions. And Firebase use Google App Engine as its backup, so it's very easy to use all the APIs provided by Google, like Google Maps API.


## Authentification module
![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/design/Sketch/Desgin/result%20portait/SIGN%20IN.png)
This design portait shows three ways of authentification : 
- Register and sign in with email and password
- Sign in with Google account
- Sign in with Facebook account
All these three ways are easy to be realised by using Firebase Authentification service.

This design portait also show the case of forgetting password. In that case, we can use the Firebase Cloud message service to send the email to users.

### For Now
For the version 01, we have realised the first way of authentification : create an account with email and password.

User enters email and password and click the Register button. 

![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/tutorial/login_screenshot.png) 

Then on the Firebase console->Authentification, we can see the information of this registered user.

![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/tutorial/authentification.png)

Then the user can use this email and password to sign in. This app can only be used when the user creates an account. 

## Localisation module (GPS)
![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/design/Sketch/Desgin/result%20portait/Localisation.png)

The module should use Google Maps API to show the map in a fragment in android app first. 
Then, we have two kinds of realisation:
- Search the places by entering the zipcode. 

![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/tutorial/map_enter_zipcode_screenshot.png)
![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/tutorial/android_studio_logic_classes.png)
![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/tutorial/android_studio_layout.png)

In this case, it should store the information about the locations of this zipcode entered on the server. When the user enters this zipcode in this app, it will retrieve the information corresponding to this location from the server firebase and then show these information in recycle view. 

- Search the places by entering the real address or part of address like what we do in real life.
This is the most ideal case. But in order to realise this case, it should use another API - Google Places API.

### For now

We use model/OneSpecificLocation to simulate the information of one specific location and services/DataService to simulate the server, to pretend the performance of retrieving data from the server.

### What we should do next?

1. Localisation/GPS module can only be test on the real device. The android studio emulator and the genymotion emulator cannot well function.
2. Use the Google Places API to replace the zipcode way.
  
  - [Google Maps API - Geocoding](https://developers.google.com/maps/documentation/geocoding/start?authuser=0)
  
  - [Displaying a Location Address - Geocoding](https://developer.android.com/training/location/display-address.html)
  
  - [Google Places API - Place Autocomplete](https://developers.google.com/places/android-api/autocomplete) 
  
  - [Google Places API - Getting Started](https://developers.google.com/places/android-api/start)

3. Get the latitude and longitude of the place the user searched in order to store them in the realtime database of firebase.
4. Then we can retrieve the capteurs information stored on the realtime databese of Firebase corresponding these latitude and longitude. And show them in the recycle view showed up once the user enters and searches by the address.
  
  - [Firebase - Read and Write Data on Android](https://firebase.google.com/docs/database/android/read-and-write)

## Capteurs information 

This module should show the information of the capteurs of the device and then restore these information in realtime database of Firebase under the current user's id and the time and the current location of this restorage. So that we can use the algorithm of Data Mining to calculate these information of these times to get the best usage of battery for this location.

### For now

![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/tutorial/battery_info_screenshot.png)

Click the Save Data button, then these datas will be stored in realtime database of firebase (this image is captured by emulator).

This part of code in **activities/CapteurDisplayerActivity** show how to write these information to the realtime database of firebase. 

![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/tutorial/realtime_database_firebase_code.png)

![ScreenShot](https://github.com/amelieykw/Smart-Battery/blob/tutorial/realtime_database.png)

#### Realtime Database of Firebase

Realtime Database of Firebase is NoSQL which is extremely simple and easy to use. It can rewrite the same data without doing any further work.

### What we should do next?

1. Get the latitude and longitude of the place the user searched.
2. Restore the capteurs' information under the corresponding location (by using the latitude and longitude of step 1 above).    
3. Add a category of time. Store the capteurs' information of one location under different time, so that we can use these information to calculate.


## Built With

* Google Maps API 
* Google Places API
* Google Firebase
* Android Studio

## Versioning

Version 01

## Authors

* **YU Kaiwen** - *Initial work*
* **SEREE Yann** - *Initial work*

