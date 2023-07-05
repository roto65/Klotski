# Klotski

[![Gradle Build](https://github.com/roto65/Klotski/actions/workflows/gradleBuild.yml/badge.svg)](https://github.com/roto65/Klotski/actions/workflows/gradleBuild.yml)
[![Gradle Test](https://github.com/roto65/Klotski/actions/workflows/gradleTest.yaml/badge.svg)](https://github.com/roto65/Klotski/actions/workflows/gradleTest.yaml)
[![pages-build-deployment](https://github.com/roto65/Klotski/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/roto65/Klotski/actions/workflows/pages/pages-build-deployment)
[![Static Badge](https://img.shields.io/badge/Contributors-3-blue)](#contributors)
[![Static Badge](https://img.shields.io/badge/Java-17-red)](https://docs.oracle.com/en/java/javase/17/docs/api/)
[![Static Badge](https://img.shields.io/badge/Licence-MIT-orange)](https://github.com/roto65/Klotski/blob/d3989182fab2d6030c4cdfa95b921d4df40377ab/LICENSE)

---

## Project description

In this project we recreated the classic **Klotski** wooden sliding puzzle.

Some features had been added, such as:

- **420** unique levels
- **hint** function to help the player in the hardest levels
- **undo** and **redo** functions for all the player's moves
- **save** and **load** functions useful when the current game needs to be interrupted or resumed

**NOTE**: the level selection feature requires an Internet connection to work.

---

## How to play Klotski

To win the game you need to move the **big red block** in the **bottom-central position** of the game board.

The commands are very simple: you can drag blocks inside the board by just left-clicking on the block you want to move
and releasing the mouse button once your cursor is sitting on top of the position you want to move the block in.

It's a matter of *dragging* and *dropping* blocks around until you reach the victory!

---

## Cloning this repository

This repository includes a submodule that it is not mandatory for the correct execution of the program. If you want to 
clone all the code, including the files in the submodule use the following command:

``` batch
git clone --branch master --recurse-submodules https://github.com/roto65/Klotski.git
```

---

## Technologies used in this project

During the development of this project we used the following technologies:

- **Java**  

    We used the **Java 17** programming language to develop every aspect of this project, including all the program's logic,
    the user interfaces and the interaction with external services like our database <br><br>

  - **javax.swing** and **java.awt**
  
      The **GUI** is entirely made using the components provided by the swing and awt modules found in the standard library <br><br>

- **Gradle**
    
    This project uses the **Gradle 8.1.1** build tool to manage [dependencies](#dependencies), compile the code, create documentation and 
    execute automated tests <br><br>


- **JUnit**

    We used the **JUnit 5** testing framework to create and execute all the unit tests for this project <br><br>

- **MongoDB**

    All the level data is stored in the cloud in a **MongoDb** cluster to reduce the overall dimension of the program
    executable and offer a better game experience at the same time <br><br>

- **GitHub**

    We used **Git** as our version control software and **GitHub** as a hosting service for the entire project <br><br>

  - **GitHub Actions**
    
    The project's **CI** is handled by GitHub using custom actions <br><br>

  - **GitHub Pages** 

    The project's documentation, including JavaDocs and test reports, is hosted by GitHub and can be viewed at 
    [this page](https://roto65.github.io/Klotski/)

    

## Dependencies

This projects uses some external libraries to enhance the standard Java capabilities, these include:

- **Gson**
  
    A simple library made by **Google** that allows us to interact with external files in the ```.json``` format <br><br>

- **MongoDb drivers**

    A library that contains the drivers necessary to establish a connection with the **Mongo Database** and execute the
    *CRUD* operations on the database's collections <br><br>

- **Log4j**

    A logging service provided by **Apache** to collect data of the database connection <br><br>

- **Jupiter**

    The engine of the **JUnit 5** testing platform used to execute automated testing <br><br>

All the above-mentioned dependencies are downloaded form the [**Maven Central Repository**](https://central.sonatype.com/?smo=true)
and managed by **Gradle**.

---

## IDE

This project was developed entirely in JetBrain's **IntelliJ IDEA 2023**.

If you want to clone this repository and take a look at the code we strongly suggest to use the same IDE as the 
repository already contains all the configuration files to run gradle scripts.

---

## Executables and platforms

In the [*Release*](https://github.com/roto65/Klotski/releases/tag/Stable) section of this repository you can find a stable release of the Klotski game in the form of a ```.jar```
file. That file can be executed on every desktop platform if the associated *Java Runtime Environment* (JRE) is correctly
installed and configured.

Required Java version: **17 or above**

To run the application you can double-click on the ```Klotski-x.jar``` file or type the following command in your preferred
command shell:

``` batch
java -jar Klotski-x.jar
```
where the 'x' represents the version of the executable you downloaded or built.

Execution was tested on the following platforms using **OpenJdk 17**:

- Windows 10 / 11
- Ubuntu 22 LTS
- Debian 12

---

## Gradle commands

If you want to clone this repository to compile new bytecode or re-run the tests you may need some of these commands:

- ```.\gradlew build ``` builds the current projects compiling all the ```.java``` files in new ```.class``` <br><br>

- ```.\gradlew jar ``` creates a new ```.jar``` package in the ```build/libs``` folder inside the project <br><br>

- ```.\gradlew javadoc ``` creates new documentation files that can be accessed by opening the ```index.html``` file, 
located in the ```build/docs/javadoc``` folder, with your web browser of choice <br><br>

- ```.\gradlew test ``` runs all the tests coded for this project and the results can be accessed by opening the 
```index.html``` file, located in the ```build/reports/tests/test``` folder, with your web browser of choice <br><br>

For further information please refer to the [Gradle documentation](https://docs.gradle.org/8.1.1/userguide/userguide.html).

---

## Saved files

The Klotski game includes a **save** features that allows the player to export all the current game data to a ```.json```
file. In this way a game can be resumed at any time and even on a different computer.

Any alteration of the exported files may result in unwanted behaviour or sometimes even complete crashes of the application.

---


## Javadocs and test reports

All the documentation related to this project, including **JavaDocs** for all the classes and **Test reports** can be
found at [this page](https://roto65.github.io/Klotski/).

---

## Levels credits

All the levels that can be played in this version of Klotski were derived from **Simon Hung**'s Klotski
repository, found at [this page](https://github.com/SimonHung/Klotski).

All the data manipulation needed to create all the **420** levels was handled in a python side project, and it's repository
can be found [here](https://github.com/roto65/KlotskiLevelGenerator). This repo also includes all the level files in the
```.json``` format!

---

## Licence

This project is licensed under the **MIT License**.

For further information please refer to [LICENSE](https://github.com/roto65/Klotski/blob/master/LICENSE).

---

## Font License

This project includes a font licensed under the **OFL License**.

For further information please refer to the [OFL license](https://github.com/roto65/Klotski/blob/master/OFL).

---

## Contributors

This project was carried out by:

|        Name         | Badge   |
|:-------------------:|---------|
|  Francesco Ariani   | 2041835 |
| Simone Pietrogrande | 2032448 |
| Alessandro Rotondo  | 2032447 |