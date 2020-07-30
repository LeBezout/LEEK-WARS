package com.leekwars.utils.reports;

/**
 * Icones ou pictogrammes disponibles dans l'interface LW
 * @author Bezout
 * @version 1.4.1
 * @since 1.7.1
 */
public enum LWIcons {
    // Flag de combats
    FIGHT_FLAG_PERFECT("https://leekwars.com/image/fight_flag/perfect.png", "Perfect", "Perfect"),
    FIGHT_FLAG_STATIC("https://leekwars.com/image/fight_flag/static.png", "Static", "Static"),
    // Drapeaux de pays
    COUNTRY_FLAG_FR("https://leekwars.com/image/flag/32/fr.png", "Language=FR", "Langue=FR"),
    COUNTRY_FLAG_EN("https://leekwars.com/image/flag/32/gb.png", "Language=EN", "Langue=EN"),
    // Divers
    DEAD("https://leekwars.com/image/cross.png", "Defeat", "Défaite"),
    GARDEN("https://leekwars.com/image/icon/garden.png", "Garden", "Potager"),
    FIGHT("https://leekwars.com/image/notif/fight.png", "Fight", "Combat"),
    TEAM_FIGHT("https://leekwars.com//image/notif/team_fight.png", "Team Fight", "Combat d'équipe"),
    RANKING("https://leekwars.com/image/icon/ranking.png", "Ranking", "Classement"),
    GEAR("https://leekwars.com/image/gearing_small_white.png", "Config", "Paramétrage"),
    SEARCH("https://leekwars.com/image/search.png", "Search", "Rechercher"),
    MAIL("https://leekwars.com/image/icon/message.png", "Mail", "Message"),
    TOURNAMENT_VICTORY("https://leekwars.com//image/notif/tournament_win.png", "Victory", "Victoire"),
    NOTIFICATION("https://leekwars.com/image/icon/notification.png", "Notification", "Notification"),
    TALENT("https://leekwars.com/image/icon/talent.png", "Talent", "Talent"),
    CONSOLE("https://leekwars.com/image/console.png", "Console", "Console"),
    HAB("https://leekwars.com/image/hab.png", "Hab", "Hab"),
    CRYSTAL("https://leekwars.com/image/crystal.png", " Crystal", "Cristal"),
    // Entités
    LEEK("https://leekwars.com/image/icon/house.png", "Leek", "Poireau"),
    FARMER("https://leekwars.com/image/trophy/artist.png", "Farmer", "Éleveur"),
    TEAM("https://leekwars.com/image/icon/team.png", "Team", "Équipe"),
    // Menu
    MARKET("https://leekwars.com/image/icon/market.png", "Market", "Marché"),
    EDITOR("https://leekwars.com/image/gearing_small_white.png", "Editor", "Éditeur"),
    TROPHY("https://leekwars.com/image/icon/trophy.png", "Trophy", "Trophée"),
    HELP("https://leekwars.com/image/icon/help.png", "Help", "Aide"),
    CHAT("https://leekwars.com/image/icon/forum.png", "Chat", "Discussions"),
    FORUM("https://leekwars.com/image/icon/forum.png", "Forum", "Salons de discussions"),
    RESPONSE("https://leekwars.com/image/notif/forum_response.png", "Response", "Réponse"),
    // Flèches
    LEFT("https://leekwars.com/image/chevron_left_white.png", "Fold", "Replier"),
    RIGHT("https://leekwars.com/image/chevron_right_white.png", "Unfold", "Déplier"),
    EXPAND("https://leekwars.com//image/expand.png", "Expand", "Déplier"),
    COLLAPSE("https://leekwars.com/image/collapse.png", "Collapse", "Replier"),
    ;

    private final String mURL;
    private final String mTitleEN;
    private final String mTitleFR;
    private LWIcons(final String pURL, final String pTitleEN, final String pTitleFR) {
        mURL = pURL;
        mTitleEN = pTitleEN;
        mTitleFR = pTitleFR;
    }

    /**
     * URL de l'image sur le site static de LW
     * @return url/uri
     */
    public String getURL() {
        return mURL;
    }

    /**
     * Code HTML de l'image qui sera affichée dans la taille spécifiée par width et height en pixels
     * @param lang fr/en
     * @param width largeur en px
     * @param height hauteur en px
     * @return code HTML
     */
    public String getHTML(final String lang, final int width, final int height) {
        return String.format("<img src=\"%s\" title=\"%s\" width=\"%dpx\" height=\"%dpx\"/>", mURL, "fr".equals(lang) ? mTitleFR : mTitleEN, width, height);
    }

    /**
     * Code HTML de l'image qui sera affichée dans sa taille d'origine
     * @param lang fr/en
     * @return code HTML
     */
    public String getHTML(final String lang) {
        return String.format("<img src=\"%s\" title=\"%s\"/>", mURL, "fr".equals(lang) ? mTitleFR : mTitleEN);
    }
}
