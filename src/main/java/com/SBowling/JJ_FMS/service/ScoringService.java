package com.SBowling.JJ_FMS.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.SBowling.JJ_FMS.model.Alliance;
import com.SBowling.JJ_FMS.model.EventType;
import com.SBowling.JJ_FMS.model.Match;
import com.SBowling.JJ_FMS.model.ScoreEvent;
import com.SBowling.JJ_FMS.model.Team;
import com.SBowling.JJ_FMS.repository.MatchRepository;
import com.SBowling.JJ_FMS.repository.TeamRepository;

@Service
public class ScoringService {
    private final MatchRepository repository;
    private final StandingsService standingsService;
    private final TeamRepository teamRepository;

    public ScoringService(MatchRepository matchRepository, StandingsService standingsService, TeamRepository teamRepository) {
        this.repository = matchRepository;
        this.standingsService = standingsService;
        this.teamRepository = teamRepository;
    }

    public Match createMatch(int b1, int b2, int r1, int r2) {
        Team blue1 = teamRepository.findById(b1).orElseThrow();
        Team blue2 = teamRepository.findById(b2).orElseThrow();
        Team red1 = teamRepository.findById(r1).orElseThrow();
        Team red2 = teamRepository.findById(r2).orElseThrow();
        
        Match match = new Match(blue1, blue2, red1, red2);
        return repository.save(match);
    }

    public Match addEvent(Long matchID, Alliance alliance, EventType eventType) {
        Match match = repository.findById(matchID).orElseThrow();

        ScoreEvent event = new ScoreEvent(alliance, eventType);
        match.addEvent(event);

        return repository.save(match);
    }

    public Map<Alliance, Integer> getScore(Long matchID) {
        Match match = repository.findById(matchID).orElseThrow();

        int blue = 0;
        int red = 0;

        for (ScoreEvent event : match.getEvents()) {
            if (event.getAlliance() == Alliance.BLUE) {
                blue += event.getPoints();
            } else {
                red += event.getPoints();
            }
        }

        Map<Alliance, Integer> result = new HashMap<>();
        result.put(Alliance.RED, red);
        result.put(Alliance.BLUE, blue);

        return result;
    }

    public void applyRecordChanges(Long matchID) {
        Match match = repository.findById(matchID).orElseThrow();

        Team blue1 = match.getBlueAlliance1();
        Team blue2 = match.getBlueAlliance2();
        Team red1 = match.getRedAlliance1();
        Team red2 = match.getRedAlliance2();

        int blueScore = getScore(matchID).get(Alliance.BLUE);
        int redScore = getScore(matchID).get(Alliance.RED);

        if (blueScore > redScore) {
            blue1.setWins(blue1.getWins() + 1);
            blue2.setWins(blue2.getWins() + 1);
            red1.setLosses(red1.getLosses() + 1);
            red2.setLosses(red2.getLosses() + 1);
        } else if (redScore > blueScore) {
            red1.setWins(red1.getWins() + 1);
            red2.setWins(red2.getWins() + 1);
            blue1.setLosses(blue1.getLosses() + 1);
            blue2.setLosses(blue2.getLosses() + 1);
        } else {
            blue1.setTies(blue1.getTies() + 1);
            blue2.setTies(blue2.getTies() + 1);
            red1.setTies(red1.getTies() + 1);
            red2.setTies(red2.getTies() + 1);
        }

        teamRepository.save(blue1);
        teamRepository.save(blue2);
        teamRepository.save(red1);
        teamRepository.save(red2);
    }

    public void applyTeamPointChanges(Long matchID) {
        Match match = repository.findById(matchID).orElseThrow();

        Team blue1 = match.getBlueAlliance1();
        Team blue2 = match.getBlueAlliance2();
        Team red1 = match.getRedAlliance1();
        Team red2 = match.getRedAlliance2();

        if (match.isFinalized()) {
            throw new IllegalStateException("Cannot apply point change to a finalized match");
        }

        int blueScore = getScore(matchID).get(Alliance.BLUE);
        int redScore = getScore(matchID).get(Alliance.RED);

        blue1.addScore(blueScore);
        blue2.addScore(blueScore);
        red1.addScore(redScore);
        red2.addScore(redScore);
        
        teamRepository.save(blue1);
        teamRepository.save(blue2);
        teamRepository.save(red1);
        teamRepository.save(red2);
    }

    public void applyTeamChanges(Long matchID) {
        applyRecordChanges(matchID);
        applyTeamPointChanges(matchID);
    }

    public Match finalizeMatch(Long matchID) {
        Match match = repository.findById(matchID).orElseThrow();

        if (match.isFinalized()) return match;

        if (!match.isTeamChangesApplied()) {
            applyTeamChanges(matchID);
            match.setTeamChangesApplied(true);
        }

        if (!match.isRankingsApplied()) {
            standingsService.applyMatchResults(matchID);
            match.setRankingsApplied(true);
        }

        match.setFinalized(true);
        return repository.save(match);
    }
}
