package com.SBowling.JJ_FMS.service;

import org.springframework.stereotype.Service;

import com.SBowling.JJ_FMS.model.MatchAlliance;
import com.SBowling.JJ_FMS.model.Team;
import com.SBowling.JJ_FMS.repository.PlayoffAllianceRepository;
import com.SBowling.JJ_FMS.repository.TeamRepository;

@Service
public class AllianceSelectionService {
    private final TeamRepository teamRepository;
    private final PlayoffAllianceRepository playoffAllianceRepository;

    public AllianceSelectionService(TeamRepository teamRepository, PlayoffAllianceRepository playoffAllianceRepository) {
        this.teamRepository = teamRepository;
        this.playoffAllianceRepository = playoffAllianceRepository;
    }

    public void createAlliance(int team1, int team2) {
        Team t1 = teamRepository.findById(team1).orElseThrow();
        Team t2 = teamRepository.findById(team2).orElseThrow();

        if (t1.isHasPlayoffAlliance() || t2.isHasPlayoffAlliance()) {
            throw new IllegalStateException("One or both teams are already in an alliance");
        }

        t1.setHasPlayoffAlliance(true);
        t2.setHasPlayoffAlliance(true);
        
        MatchAlliance alliance = new MatchAlliance(t1, t2);
        playoffAllianceRepository.save(alliance);
    }
}
