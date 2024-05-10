package com.almosi9are.controleiptv;

public class math_info {
    String get_id_doc;
    String TeamA;
    String TeamB;
    String TeamA_flag;
    String TeamB_flag;
    String date_debut;
    String date_fin;
    String Url;
    String dawri;
    String channel;
    String commentaire;
    String desc;
    String teamA_score;
    String teamB_score;
    String Terrain;
    String goals_teamA;
    String goals_teamB;

    public String getGoals_teamA() {
        return goals_teamA;
    }

    public void setGoals_teamA(String goals_teamA) {
        this.goals_teamA = goals_teamA;
    }

    public String getGoals_teamB() {
        return goals_teamB;
    }

    public void setGoals_teamB(String goals_teamB) {
        this.goals_teamB = goals_teamB;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getGet_id_doc() {
        return get_id_doc;
    }

    public void setGet_id_doc(String get_id_doc) {
        this.get_id_doc = get_id_doc;
    }

    public String getDawri() {
        return dawri;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getTeamA_score() {
        return teamA_score;
    }

    public void setTeamA_score(String teamA_score) {
        this.teamA_score = teamA_score;
    }

    public String getTeamB_score() {
        return teamB_score;
    }

    public void setTeamB_score(String teamB_score) {
        this.teamB_score = teamB_score;
    }

    public void setDawri(String dawri) {
        this.dawri = dawri;
    }

    public math_info() {
    }

    public math_info(String get_id_doc, String teamA, String teamB, String teamA_flag, String teamB_flag, String date_debut, String date_fin, String url, String dawri, String channel, String commentaire, String desc, String teamA_score, String teamB_score, String terrain, String goals_teamA, String goals_teamB) {
        this.get_id_doc = get_id_doc;
        TeamA = teamA;
        TeamB = teamB;
        TeamA_flag = teamA_flag;
        TeamB_flag = teamB_flag;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        Url = url;
        this.dawri = dawri;
        this.channel = channel;
        this.commentaire = commentaire;
        this.desc = desc;
        this.teamA_score = teamA_score;
        this.teamB_score = teamB_score;
        Terrain = terrain;
        this.goals_teamA = goals_teamA;
        this.goals_teamB = goals_teamB;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    public String getTerrain() {
        return Terrain;
    }

    public void setTerrain(String terrain) {
        Terrain = terrain;
    }

    public String getTeamA() {
        return TeamA;
    }

    public void setTeamA(String teamA) {
        TeamA = teamA;
    }

    public String getTeamB() {
        return TeamB;
    }

    public void setTeamB(String teamB) {
        TeamB = teamB;
    }

    public String getTeamA_flag() {
        return TeamA_flag;
    }

    public void setTeamA_flag(String teamA_flag) {
        TeamA_flag = teamA_flag;
    }

    public String getTeamB_flag() {
        return TeamB_flag;
    }

    public void setTeamB_flag(String teamB_flag) {
        TeamB_flag = teamB_flag;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
