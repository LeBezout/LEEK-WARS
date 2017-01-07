# LEEK-WARS
Leek Wars JAVA Utilities for Farmers - version 1.3

## Liens
  * URL du site Leek Wars : https://leekwars.com
  * URL racine de l'API Leek Wars : https://leekwars.com/api
  * URL de l'aide de l'API Leek Wars : https://leekwars.com/help/api
  * Sujet sur le forum : https://leekwars.com/forum/category-7/topic-8059

## Outillage implémenté
  * Connexion / déconnexion
  * Réinitialisation des registres
  * Vérification du seuil des registres (limite à 100)
  * Lancement de combats (poireau, éleveur, équipe)
  * Ultra Fast Garden : lancer tous les combats d'un poireau (ou de tous les poireaux)
  * Ultra Fast Garden : lancer tous les combats d'un éleveur
  * Ultra Fast Garden : lancer tous les combats d'une composition d'équipe
  * Trace des flux JSON

## Versions
  * 1.0 : version initiale
  * 1.1 : prise en compte des changements dans l'API du potager suite à la version 1.92 de LeekWars
  * 1.2 : ajout de méthodes pour la récupération des différents classements
  * 1.3 : prise en compte des changements dans l'API du potager suite à la version 1.94 de LeekWars

## Infos développeurs

### Environnement
  * Workspace en UTF-8
  * Maven 3
  * Java 7
  * Libs : JUnit 4, log4j, google gson
    
### Eléments de l'API utilisés
  * Connexion, récupération du token : `farmer/login-token/[name]/[password]`
  * Invalidation du token : `farmer/disconnect/[token]`
  * Récupération/Mise à jour des infos de l'éleveur : `farmer/get/[farmer_id]`
  * Récupérer les infos de l'équipe : `team/get/[team_id]`
  * Récupérer les infos privées de l'équipe : `team/get-private/[team_id]/[token]`
  * Récupérer le potager : `garden/get/[token]`
  * Récupérer le potager d'un poireau : `garden/get-leek-opponents/[leek_id]/[token]` (depuis la 1.92 de LW)
  * Récupérer le potager d'un éleveur : `garden/get-farmer-opponents/[token]` (depuis la 1.92 de LW)
  * Récupérer le potager d'une composition d'équipe : `garden/get-composition-opponents/[compo_id]/[token]` (depuis la 1.92 de LW)
  * Lancement d'un combat éleveur : `garden/start-farmer-fight/[target_id]/[token]`
  * Lancement d'un combat solo : `garden/start-solo-fight/[leek_id]/[target_id]/[token]`
  * Lancement d'un combat d'équipe : `garden/start-team-fight/[compo_id]/[target_id]/[token]`
  * Récupérer les infos d'un combat : `fight/get/[fight_id]`
  * Inscription aux tournois éléveurs : `farmer/register-tournament/[token]`
  * Inscription aux tournois solos : `leek/register-tournament/[leek_id]/[token]`
  * Inscription aux tournois d'équipe : `team/register-tournament/[compo_id]/[token]`
  * Récupérer les registres d'un poireau : `leek/get-registers/[leek_id]/[token]`
  * Positionner un registre d'un poireau : `leek/set-register/[leek_id]/[key]/[value]/[token]`
  * Supprimer un registre d'un poireau `leek/delete-register/[leek_id]/[key]/[token]`
  * Lister les trophées de l'éleveur : `trophy/get-farmer-trophies/[farmer_id]/[lang]/[token]`
  * Obtenir le classement de l'éleveur : `ranking/get-farmer-rank/[farmer_id]/[order=talent|name|total_level]`
  * Obtenir le classement d'un poireau : `ranking/get-leek-rank/[leek_id]/[order=talent|name|level]`
  * Obtenir les classements Fun : `ranking/fun/[token]`

### Configuration HTTPS
  * Récupérer les fichiers `leekwars-utils/src/main/security/jssecacerts` et `leekwars-utils/src/main/security/lw.jks`
  * Les copier dans un dossier. Exemples "res", "resources",  ...
  * Rajouter aux options de la JVM :
   * `-Djavax.net.ssl.keyStore=${APP_HOME}/res/lw.jks`
   * `-Djavax.net.ssl.trustStore=${APP_HOME}/res/jssecacerts`
  * Ou dans un test unitaire :

```java
	@BeforeClass
	public static void init() {
		System.setProperty("javax.net.ssl.keyStore", "/chemin/vers/lw.jks");
		System.setProperty("javax.net.ssl.trustStore", "/chemin/vers/jssecacerts");
	}
```
