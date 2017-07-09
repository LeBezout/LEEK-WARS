# LEEK-WARS - Utility and API Project

## Environnment
  * UTF-8 IDE Workspace
  * Maven 3
  * Java SE 7
  * Libs : JUnit 4, log4j 1.2, google gson 2.3

## How To Build
To build the latest standalone jar 
   `mvn clean package`

To build the latest exe file :
   `mvn clean package -Pwindows`
   
## How to generate trustStore file
  * Run the java class `com.leekwars.utils.tools.InstallCert`
  * Wait for `Enter certificate to add to trusted keystore or 'q' to quit: [1]` message
  * Enter 1
  * `jssecacerts` file is generated in project root folder
