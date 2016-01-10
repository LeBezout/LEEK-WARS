# LEEK-WARS
Leek Wars JAVA Utilities for Farmers

## Liens
  * URL du site Leek Wars : http://leekwars.com
  * URL racine de l'API Leek Wars : http://leekwars.com/api
  * URL de l'aide de l'API Leek Wars : http://leekwars.com/help/api

## Eléments de l'API utilisés
  * Connexion, récupération du token : <tt>farmer/login-token/[name]/[password]</tt>
  * Invalidation du token : <tt>farmer/disconnect/[token]</tt>
  * Récupération du potager : <tt>garden/get/[token]</tt>
  * Inscription aux tournois éléveurs : <tt>farmer/register-tournament/[token]</tt>
  * Inscription aux tournois solos : <tt>leek/register-tournament/[leek_id]/[token]</tt>
  * Lancement d'un combat éleveur : <tt>garden/start-farmer-fight/[target_id]/[token]</tt>
  * Lancement d'un combat solo : <tt>garden/start-solo-fight/[leek_id]/[target_id]/[token]</tt>
  * Récupération des infos d'un combat : <tt>fight/get/[fight_id]</tt>
