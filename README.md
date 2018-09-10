# Extension-Repository

[![Build Status](https://travis-ci.org/PenyoKolev/Extension-Repository.svg?branch=master)](https://travis-ci.org/PenyoKolev/Extension-Repository) [![codecov](https://codecov.io/gh/PenyoKolev/Extension-Repository/branch/master/graph/badge.svg)](https://codecov.io/gh/PenyoKolev/Extension-Repository)
--------
## Telerik Academy Alpha Final Project
----------

### Project description
Extension Repository is a web application which makes it possible for developers to perform the following actions:

**browsing**, **uploading** and **downloading** of extensions.

The application has 3 levels of accessibility:
#### Public part
A Guest user can browse through the extensions (only extensions approved by administrator), search and download them.
#### Private part(Users only)
Registered users have the same options as guest users, including also the operations:
* Upload extensions
* Edit extensions (owned extensions only)
* Delete extensions (owned extensions only)
#### Admin part
An administrator have the same options as quest users, plus the following:
* Approve extensions
* Edit / Delete extensions (all)
* Feature extensions 
* Enable / Disable user accounts
* Set refresh time for all extensions
* Force data refresh


#### Getting started

##### Prerequisites
* [MariaDB](https://mariadb.com)
* [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

##### Run instructions
* Download/clone the project from GitHub

    https://github.com/PenyoKolev/Extension-Repository
* Execute the sql
    
    $ mysql -u root -p < extensions-final-version.sql
* Run the application
    
    $ ./gradlew bootRun
    
Locally the app is set up to work on port 8080.
#### Rest API

All public features are accessible via Rest API

* Get all extensions
    
    http://localhost:8080/api/extension/all
* Find extension by id

    http://localhost:8080/api/extension/{id}
* Get featured extensions

    http://localhost:8080/api/extension/featured
* Search by tag name
    
    http://localhost:8080/api/extension/tag/{name}
* Browse extensions with search criteria

    http://localhost:8080/api/extension/browse
* Download extensions

    http://localhost:8080/api/extension/download/{id}


#### Screenshots
###### Structure
![project structure](https://i.imgur.com/ydMNpJz.png)
###### Homepage
![index](https://i.imgur.com/ji4HcbF.png)
###### Browse
![browse](https://i.imgur.com/IUR7PQ6.png)
###### Extension
![extension](https://i.imgur.com/ZQMePc8.png)
###### Admin
![admin](https://i.imgur.com/7vM4Ohn.png)
###### Synchronisation
![synchronisation](https://i.imgur.com/q3LuiLq.png)

#### Authors
Yasen Stoilov , Penyo Kolev
