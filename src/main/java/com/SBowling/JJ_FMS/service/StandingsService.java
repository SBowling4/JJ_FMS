package com.SBowling.JJ_FMS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.SBowling.JJ_FMS.model.Team;

@Service
public class StandingsService {
    private final RankingPointService rankingPointService;

    private List<Team> standings = new ArrayList<>();

    public StandingsService(RankingPointService rankingPointService) {
        this.rankingPointService = rankingPointService;
    }

    
}
