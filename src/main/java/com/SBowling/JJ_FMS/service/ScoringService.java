package com.SBowling.JJ_FMS.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.SBowling.JJ_FMS.model.Alliance;
import com.SBowling.JJ_FMS.model.EventType;
import com.SBowling.JJ_FMS.model.Match;
import com.SBowling.JJ_FMS.model.ScoreEvent;
import com.SBowling.JJ_FMS.repository.MatchRepository;

@Service
public class ScoringService {
    private final MatchRepository repository;

    public ScoringService(MatchRepository repository) {
        this.repository = repository;
    }

    public Match createMatch(int b1, int b2, int r1, int r2) {
        Match match = new Match(b1, b2, r1, r2);
        return repository.save(match);
    }

    public Match addEvent(Long matchID, Alliance alliance, EventType eventType) {
        Match match = repository.findById(matchID).orElseThrow();

        ScoreEvent event = new ScoreEvent(alliance, eventType);
        match.addEvent(event);

        return repository.save(match);
    }

    public Map<String, Integer> getScore(Long matchID) {
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

        Map<String, Integer> result = new HashMap<>();
        result.put(Alliance.RED.toString(), red);
        result.put(Alliance.BLUE.toString(), blue);

        return result;
    }
}
