# LEEK-WARS

Leek Wars JAVA Utilities for Farmers - version 1.8.4

## Liens

* URL du site Leek Wars : <https://leekwars.com>
* URL racine de l'API Leek Wars : <https://leekwars.com/api>
* URL de l'aide de l'API Leek Wars : <https://leekwars.com/help/api>
* Sujet sur le forum : <https://leekwars.com/forum/category-7/topic-8059>

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
* 1.4 : prise en compte de la version de l'API, ajout d'un "main" pour le fastgarden
* 1.5 : prise en compte de la version 2.0.2 de LeekWars : le token est désormais passé au format JWT le header `Authorization: Bearer <token>`
* 1.6 : prise en compte des évolutions de l'API (après la 2.0.2) : erreur via le statut HTTP désormais
* 1.6.1 : prise en compte du changement dans l'API sur la récupération d'un combat
* 1.7 : migration log4j 1.x vers SLF4J / logback
* 1.7.1 : correction logging + nouveau chemin des images statiques depuis la 2.12.0
* 1.8 : migration Java SE 8 et JUnit 5, correction récupération des trophées suite changement dans l'API v2.25
* 1.8.1 : correction affichage des flags
* 1.8.2 : correction suite à _breaking change_ dans l'API de login depuis la 2.27.0
* 1.8.3 : amélioration HttpUtils suite à erreur "429 Too many request"
* 1.8.4 : correction suite à _breaking change_ dans l'API de récupération d'un combat d'équipe, Upgrade dependencies
* 1.8.5 : correction suite à _breaking change_ dans l'API de login depuis la 2.29.0
* 1.9.0 : correction suite à _breaking change_ dans l'API depuis la 2.31.0 (get -> post) + fix "disconnect"

## Infos développeurs

### Environnement

* Workspace en UTF-8
* Maven 3
* Java 8
* Libs : JUnit 5, slf4j, google gson
    
### Eléments de l'API utilisés

* Connexion, récupération du token : `farmer/login` (POST depuis la 2.31.0 de LW)
* Invalidation du token : `farmer/disconnect` (POST depuis ma 2.31.0 de LW)
* Récupération/Mise à jour des infos de l'éleveur : `farmer/get/[farmer_id]`
* Récupérer les infos de l'équipe : `team/get/[team_id]`
* Récupérer les infos privées de l'équipe : `team/get-private/[team_id]/[token]`
* Récupérer le potager : `garden/get/[token]`
* Récupérer le potager d'un poireau : `garden/get-leek-opponents/[leek_id]/[token]` (depuis la 1.92 de LW)
* Récupérer le potager d'un éleveur : `garden/get-farmer-opponents/[token]` (depuis la 1.92 de LW)
* Récupérer le potager d'une composition d'équipe : `garden/get-composition-opponents/[compo_id]/[token]` (depuis la 1.92 de LW)
* Lancement d'un combat éleveur : `garden/start-farmer-fight` (POST depuis la 2.31.0 de LW)
* Lancement d'un combat solo : `garden/start-solo-fight` (POST depuis la 2.31.0 de LW)
* Lancement d'un combat d'équipe : `garden/start-team-fight` (POST depuis la 2.31.0 de LW)
* Récupérer les infos d'un combat : `fight/get/[fight_id]`
* Inscription aux tournois éleveurs : `farmer/register-tournament/[token]`
* Inscription aux tournois solos : `leek/register-tournament/[leek_id]/[token]`
* Inscription aux tournois d'équipe : `team/register-tournament/[compo_id]/[token]`
* Récupérer les registres d'un poireau : `leek/get-registers/[leek_id]/[token]`
* Positionner un registre d'un poireau : `leek/set-register/[leek_id]/[key]/[value]/[token]`
* Supprimer un registre d'un poireau `leek/delete-register/[leek_id]/[key]/[token]`
* Lister les trophées de l'éleveur : `trophy/get-farmer-trophies/[farmer_id]/[lang]/[token]`
* Obtenir le classement de l'éleveur : `ranking/get-farmer-rank/[farmer_id]/[order=talent|name|total_level]`
* Obtenir le classement d'un poireau : `ranking/get-leek-rank/[leek_id]/[order=talent|name|level]`
* Obtenir les classements Fun : `ranking/fun/[token]`
* Obtenir la version courante de l'API : `leek-wars/version`

## How To

* [Utiliser leekwars-utils](docs/how-to.md)

## Problèmes

> `Exception in thread "main" java.awt.AWTError: Assistive Technology not found: org.GNOME.Accessibility.AtkWrapper`

> `Caused by: java.lang.ClassNotFoundException: org.GNOME.Accessibility.AtkWrapper`

:bulb: <https://bugs.debian.org/cgi-bin/bugreport.cgi?bug=822642>
