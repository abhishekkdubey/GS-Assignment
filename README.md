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
![High Level Diagram](https://github.com/abhishekkdubey/GS-Assignment/blob/develop/HLD.png)

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

