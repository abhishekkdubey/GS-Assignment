# GS-Assignment
## Hypothesis 
Display NASA’s Astronomy picture of the day.
Basic feature set would be -
-	Allow users to search for the picture for a date of their choice of Date
-	Allow users to create/manage a list of "favorite" listings
-	Display date, explanation, Title and the image / video of the day
-	App should cache information and should display last updated information in case of network unavailability



## High level Diagram
The possible solution for project is described in High-Level Diagram.
![High Level Diagram](https://github.com/abhishekkdubey/GS-Assignment/blob/develop/pics/HLD.png)

### Components:
**Backend** :Represents the NASA APOS API.

**API Service**: Abstract client-server communications from the rest of the system.

**Persistence**: A single source of truth. The data your system receives gets persisted on the disk first and then propagated to other components.

**Repository**: A mediator component between API Service and Persistence.

**HomeViewModel**: Represents a component responsible for retriving Astronomy picture of the day and mark as favourite.

**FavouriteViewModel**: Represents a components responsible for providing data of favourite Astronomy pictures.

**DI Graph**: Dependency injection graph.

**Image Loader**: Responsible for loading and caching static images. Usually represented by a 3rd-party library Glide.

**UI/UX**: Defined User experience. These derived mainly using Jetpack navigation Graph

## Detailed Design Flow
To Design this app, I have followed industry best practice design pattern and used android jetpack components. Also, considered to support **DarkMode**.

**Architecture patterns**: Used MVVM with Android jetpack ViewModel to achieve this design

**Navigation and Flow**: Android Jetpack _Navigation_ component with BottomNavihationView for better user-experience and handy screen flow.

**App Theme & Support Different Screen size**: Used material theme to provide support of DarkMode. And Desing of the UI component done keeping this in mind to support different screen Size.

**Storage**: Used Jetpack Room persistence library and SharedPreference.


## Storage Rule
Based on hypothesis, I have implementd the two different storage option. One is Room DB and another is SharedPreference. Room DB store favourite listing but Shared preference store today's APOD JSON as key-value pair.

Search APOD by date will not be cached in persistance until it mark as favourite. 

As soon as user mark any APOD as favoutire. The data will store in room perstent storage. Which will be served to Favourite screen. 

User can mark any picture as unfavourite. Which leads to remove that particular APOD from Room DB.  


## Screenshots

### Light Mode
![LightMode_01](https://github.com/abhishekkdubey/GS-Assignment/blob/develop/pics/LightMode_01.jpeg)
![LightMode_02](https://github.com/abhishekkdubey/GS-Assignment/blob/develop/pics/LightMode_02.jpeg)
![LightMode_03](https://github.com/abhishekkdubey/GS-Assignment/blob/develop/pics/LightMode_03.jpeg)

### Dark Mode

![DarkMode_01](https://github.com/abhishekkdubey/GS-Assignment/blob/develop/pics/DarkMode_01.jpeg)
![DarkMode_02](https://github.com/abhishekkdubey/GS-Assignment/blob/develop/pics/DarkMode_02.jpeg)
![DarkMode_03](https://github.com/abhishekkdubey/GS-Assignment/blob/develop/pics/DarkMode_03.jpeg)




