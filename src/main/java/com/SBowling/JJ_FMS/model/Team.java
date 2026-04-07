package com.SBowling.JJ_FMS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class Team {
    
    @Id 
    @Getter private int teamNumber;

    @Getter private String teamName;

    @Getter private int rankingPoints;
    @Getter private int wins;
    @Getter private int losses;
    @Getter private int ties;

    @Getter private int totalScores;

    public Team() {}

    public Team(int teamNumber, String teamName) {
        this.teamNumber = teamNumber;
        this.teamName = teamName;
    }

    public void addRankingPoints(int rp) {
        rankingPoints += rp;
    }

    public double getRankingPointAverage() {
        return rankingPoints / (wins + losses + ties);
    }


}
