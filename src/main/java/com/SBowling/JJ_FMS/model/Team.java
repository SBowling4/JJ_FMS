package com.SBowling.JJ_FMS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Team {
    
    @Id 
    @Getter private int teamNumber;

    @Getter private String teamName;

    @Getter private int rankingPoints;
    @Getter @Setter private int wins;
    @Getter @Setter private int losses;
    @Getter @Setter private int ties;

    @Getter private int totalScores;

    @Getter @Setter private boolean hasPlayoffAlliance = false;

    public Team() {}

    public Team(int teamNumber, String teamName) {
        this.teamNumber = teamNumber;
        this.teamName = teamName;
    }

    public void addRankingPoints(int rp) {
        rankingPoints += rp;
    }

    public double getRankingPointAverage() {
        return rankingPoints / getTotalMatches();
    }

    public int getTotalMatches() {
        return wins + losses + ties;
    }

    public void addScore(int score) {
        totalScores += score;
    }

    public double getAverageScore() {
        if (getTotalMatches() == 0) return 0;
        return (double) totalScores / getTotalMatches();
    }
}
