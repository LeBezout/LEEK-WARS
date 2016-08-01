# LEEK-WARS
Leek Wars JAVA Utilities for Farmers

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
  * Ultra Fast Garden : lancer tous les combats d'un composition d'équipe
  * Trace des flux JSON

## Versions
  * 1.0 : version initiale
  * 1.1 : prise en compte des changements dans l'API du potager suite à la version 1.92 de LeekWars
    
## Eléments de l'API utilisés
  * Connexion, récupération du token : `farmer/login-token/[name]/[password]`
  * Invalidation du token : `farmer/disconnect/[token]`
  * Récupération/Mise à jour des infos de l'éleveur : `farmer/get/[farmer_id]`
  * Récupération des infos de l'équipe : `team/get/[team_id]`
  * Récupération des infos privées de l'équipe : `team/get-private/[team_id]/[token]`
  * Récupération du potager : `garden/get/[token]`
  * Récupération du potager d'un poireau : `garden/get-leek-opponents/[leek_id]/[token]` (depuis la 1.92 de LW)
  * Récupération du potager d'un éleveur : `garden/get-farmer-opponents/[token]` (depuis la 1.92 de LW)
  * Récupération du potager d'une composition d'équipe : `garden/get-composition-opponents/[compo_id]/[token]` (depuis la 1.92 de LW)
  * Lancement d'un combat éleveur : `garden/start-farmer-fight/[target_id]/[token]`
  * Lancement d'un combat solo : `garden/start-solo-fight/[leek_id]/[target_id]/[token]`
  * Lancement d'un combat d'équipe : `garden/start-team-fight/[compo_id]/[target_id]/[token]`
  * Récupération des infos d'un combat : `fight/get/[fight_id]`
  * Inscription aux tournois éléveurs : `farmer/register-tournament/[token]`
  * Inscription aux tournois solos : `leek/register-tournament/[leek_id]/[token]`
  * Inscription aux tournois d'équipe : `team/register-tournament/[compo_id]/[token]`
  * Récupération des registres d'un poireau : `leek/get-registers/[leek_id]/[token]`
  * Positionne un registre d'un poireau : `leek/set-register/[leek_id]/[key]/[value]/[token]`
  * Supprime un registre d'un poireau `leek/delete-register/[leek_id]/[key]/[token]`

## Infos développeurs

### Envrionnement
  * Workspace en UTF-8
  * Maven 3
  * Java 7
  * Libs : JUnit 4, log4j, google gson

### Passage du site en HTTPS
  * Récupérer les fichiers `leekwars-utils/src/main/security/jssecacerts` et `leekwars-utils/src/main/security/lw.jks`
  * Les copiers dans un dossier. Exemples "res", "resources",  ...
  * Rajouter aux options de la JVM :
   * `-Djavax.net.ssl.keyStore=${APP_HOME}/res/lw.jks`
   * `-Djavax.net.ssl.trustStore=${APP_HOME}/res/jssecacerts`
