package com.SBowling.JJ_FMS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.SBowling.JJ_FMS.model.Alliance;
import com.SBowling.JJ_FMS.model.Match;
import com.SBowling.JJ_FMS.model.Team;
import com.SBowling.JJ_FMS.repository.MatchRepository;
import com.SBowling.JJ_FMS.repository.TeamRepository;

@Service
public class StandingsService {
    private final RankingPointService rankingPointService;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public StandingsService(RankingPointService rankingPointService, MatchRepository matchRepository, TeamRepository teamRepository) {
        this.rankingPointService = rankingPointService;
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public void applyMatchResults(Long matchID) {
        Match match = matchRepository.findById(matchID).orElseThrow();

        int blueRP = rankingPointService.calculateTotalRP(matchID).get(Alliance.BLUE);
        int redRP = rankingPointService.calculateTotalRP(matchID).get(Alliance.RED);

        Team blue1 = match.getBlueAlliance1();
        Team blue2 = match.getBlueAlliance2();
        Team red1 = match.getRedAlliance1();
        Team red2 = match.getRedAlliance2();

        blue1.addRankingPoints(blueRP);
        blue2.addRankingPoints(blueRP);

        red1.addRankingPoints(redRP);
        red2.addRankingPoints(redRP);

        teamRepository.save(blue1);
        teamRepository.save(blue2);
        teamRepository.save(red1);
        teamRepository.save(red2);
    }

    public List<Team> getStandings() {
    return teamRepository.findAll().stream()
            .sorted((t1, t2) -> {
                double t1Avg = t1.getTotalMatches() == 0
                        ? 0
                        : (double) t1.getRankingPoints() / t1.getTotalMatches();

                double t2Avg = t2.getTotalMatches() == 0
                        ? 0
                        : (double) t2.getRankingPoints() / t2.getTotalMatches();

                int avgCompare = Double.compare(t2Avg, t1Avg);

                if (avgCompare != 0) {
                    return avgCompare;
                }

                // Tie-breaker: average score
                return Double.compare(
                        t2.getAverageScore(),
                        t1.getAverageScore());
            })
            .toList();
}
    
}
