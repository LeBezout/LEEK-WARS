# LEEK-WARS - Utility and API Project

## Environment

* UTF-8 IDE Workspace
* Maven 3
* Java SE 8
* Libs : JUnit 5, SLF4J, Google Gson 2

## How To Build

To build the latest standalone jar : `mvn clean package`

To build the latest exe file : `mvn clean package -Pwindows`

## ~~Configuration HTTPS~~

* ~~Récupérer les fichiers `leekwars-utils/src/main/security/jssecacerts` et `leekwars-utils/src/main/security/lw.jks`~~
* ~~Les copier dans un dossier. Exemples "res", "resources",  ...~~
* ~~Rajouter aux options de la JVM :~~
    * `-Djavax.net.ssl.keyStore=${APP_HOME}/res/lw.jks`
    * `-Djavax.net.ssl.trustStore=${APP_HOME}/res/jssecacerts`
* ~~Ou dans un test unitaire :~~

```java
	@BeforeClass
	public static void init() {
		System.setProperty("javax.net.ssl.keyStore", "/chemin/vers/lw.jks");
		System.setProperty("javax.net.ssl.trustStore", "/chemin/vers/jssecacerts");
	}
```
* ~~_Les certificats LeekWars ont généralement une durée de validité de 3 mois_~~
* ~~La classe outil `com.leekwars.utils.tools.InstallCert` permet de générer le fichier "trust store"~~

## ~~How to generate trustStore file~~

* ~~Run the java class `com.leekwars.utils.tools.InstallCert`~~
* ~~Wait for `Enter certificate to add to trusted keystore or 'q' to quit: [1]` message~~
* ~~Enter 1~~
* ~~`jssecacerts` file is generated in project root folder~~
