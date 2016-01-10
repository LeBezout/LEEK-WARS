# LEEK-WARS
Leek Wars Utilities

## Liens
  * URL du site Leek Wars : http://leekwars.com
  * URL racine de l'API Leek Wars : http://leekwars.com/api
  * URL de l'aide de l'API Leek Wars : http://leekwars.com/help/api

## Elements de l'API utilisés
  * Connexion, récupération du token : ''farmer/login-token/[name]/[password]''
  * Invalidation du token : ''farmer/disconnect/[token]''
  * Récupération du potager : ''garden/get/[token]''
  * Inscription aux tournois éléveurs : ''farmer/register-tournament/[token]''
  * Inscription aux tournois solos : ''leek/register-tournament/<leek_id>/[token]''
  * Lancement d'un combat éleveur : ''garden/start-farmer-fight/<target_id>/[token]''
  * Lancement d'un combat solo : ''garden/start-solo-fight/<leek_id>/<target_id>/[token]''
  * Récupération des infos d'un combat : ''fight/get/[fight_id]''
