package com.SBowling.JJ_FMS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class MatchAlliance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter private final Team team1;
    @Getter private final Team team2;

    public MatchAlliance(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }
}
