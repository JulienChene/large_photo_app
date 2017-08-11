# LargePhoto app

This project is an Android test in Java. It has been developed by Julien Chene. It follows the architecture derivated from MVP: [Clean Architecture][1].

## Libraries used
Featured in this project are the following 
  * RxJava
  * Dagger 2
  * Retrofit
  * Glide
  * ButterKnife
  
Testing is done using
  * Espresso
  * Mockito

### Getting started and run tests

1. Download the project code, preferably using `git clone`
2. In Android Studio, select *File | Open...* and point to the `./build.gradle` file.
3. Create a test configuration with the JUnit4 runner: org.junit.runners.JUnit4
  1.Open Run menu | Edit Configurations
  2.Add a new *JUnit* configuration
  3.Choose module `app`
  4.Select the class to run by using the `...` button
4. Run the newly created configuration

### Use Gradle
After downloading the project's code using `git clone` you'll be able to run the unit tests using the command line
```
./gradlew test
```
If all the unit tests have been successful you will get a `BUILD SUCCESSFUL` message.

## How to improve the app

  * Implement better tests
  * Implement a ViewPager in the GalleryFragment
  * Implement a Repository interface to cut out Android-related classes from the actual Repository
  * Move BaseRepository content into a different class

[1]: https://medium.com/@dmilicic/a-detailed-guide-on-developing-android-apps-using-the-clean-architecture-pattern-d38d71e94029