# LEEK-WARS
Leek Wars JAVA Utilities for Farmers

## Liens
  * URL du site Leek Wars : https://leekwars.com
  * URL racine de l'API Leek Wars : https://leekwars.com/api
  * URL de l'aide de l'API Leek Wars : https://leekwars.com/help/api

## Eléments de l'API utilisés
  * Connexion, récupération du token : <tt>farmer/login-token/[name]/[password]</tt>
  * Invalidation du token : <tt>farmer/disconnect/[token]</tt>
  * Récupération/Mise à jour des infos de l'éleveur : <tt>farmer/get/[farmer_id]</tt>
  * Récupération des infos de l'équipe : <tt>team/get/[team_id]</tt>
  * Récupération des infos privées de l'équipe : <tt>team/get-private/[team_id]/[token]</tt>
  * Récupération du potager : <tt>garden/get/[token]</tt>
  * Inscription aux tournois éléveurs : <tt>farmer/register-tournament/[token]</tt>
  * Inscription aux tournois solos : <tt>leek/register-tournament/[leek_id]/[token]</tt>
  * Inscription aux tournois d'équipe : <tt>team/register-tournament/[compo_id]/[token]</tt>
  * Lancement d'un combat éleveur : <tt>garden/start-farmer-fight/[target_id]/[token]</tt>
  * Lancement d'un combat solo : <tt>garden/start-solo-fight/[leek_id]/[target_id]/[token]</tt>
  * Lancement d'un combat d'équipe : <tt>garden/start-team-fight/[compo_id]/[target_id]/[token]</tt>
  * Récupération des infos d'un combat : <tt>fight/get/[fight_id]</tt>
  * Récupération des registres d'un poireau : <tt>leek/get-registers/[leek_id]/[token]</tt>
  * Positionne un registre d'un poireau : <tt>leek/set-register/[leek_id]/[key]/[value]/[token]</tt>
  * Supprime un registre d'un poireau <tt>leek/delete-register/[leek_id]/[key]/[token]</tt>

## Infos développeurs
  * Workspace en UTF-8
  * Maven 3
  * Java 7
  * Libs : JUnit 4,  log4j, google gson

## Passage du site en HTTPS
  * Récupérer les fichiers leekwars-utils/src/main/security/jssecacerts et leekwars-utils/src/main/security/lw.jks
  * Les copiers dans un dossier. Exmple "res"
  * Rajouter aux options de la JVM :
   * `-Djavax.net.ssl.keyStore=${APP_HOME}/res/lw.jks`
   * `-Djavax.net.ssl.trustStore=${APP_HOME}/res/jssecacerts`
