package com.SBowling.JJ_FMS.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.SBowling.JJ_FMS.model.Alliance;
import com.SBowling.JJ_FMS.model.EventType;
import com.SBowling.JJ_FMS.model.Match;
import com.SBowling.JJ_FMS.model.ScoreEvent;
import com.SBowling.JJ_FMS.repository.MatchRepository;

@Service
public class RankingPointService {

    private final ScoringService scoringService;
    private final MatchRepository repository;

    private final int traversalRPThreshold = 16;
    private final int controlRPThreshold = 70;
    private final int pushRPThreshold = 20;

    public RankingPointService(ScoringService scoringService, MatchRepository repository) {
        this.scoringService = scoringService;
        this.repository = repository;
    }

    public Map<Alliance, Integer> calculateWinRP(Long matchID) {
        int blueScore = scoringService.getScore(matchID).get(Alliance.BLUE);
        int redScore = scoringService.getScore(matchID).get(Alliance.RED);


        if (blueScore > redScore) {
            return Map.of(Alliance.BLUE, 3, Alliance.RED, 0);
        } else if (redScore > blueScore) {
            return Map.of(Alliance.BLUE, 0, Alliance.RED, 3);
        } else {
            return Map.of(Alliance.BLUE, 1, Alliance.RED, 1);
        }
    }

    public Map<Alliance, Integer> calculateTraversalRP(Long matchID) {
        Match match = repository.findById(matchID).orElseThrow();

        List<ScoreEvent> events = match.getEvents();
        double blueTraversalPoints = 0, redTraversalPoints = 0;

        for (ScoreEvent event : events) {
            if (EventType.isTraversal(event.getEventType())) {
                if (event.getAlliance() == Alliance.BLUE) {
                    blueTraversalPoints += event.getPoints();
                } else {
                    redTraversalPoints += event.getPoints();
                }
            }
        }

        int blueBonusRP = blueTraversalPoints >= traversalRPThreshold ? 1 : 0;
        int redBonusRP = redTraversalPoints >= traversalRPThreshold ? 1 : 0;

        return Map.of(Alliance.BLUE, blueBonusRP, Alliance.RED, redBonusRP);
    }

    public Map<Alliance, Integer> calculateControlRP(Long matchID) {
        Match match = repository.findById(matchID).orElseThrow();

        List<ScoreEvent> events = match.getEvents();
        double blueControl = 0, redControl = 0;

        double lastBlueControl = 0, lastRedControl = 0;


        Alliance currentControl = null;

        for (ScoreEvent event : events) {
            if (event.getEventType() == EventType.BANK_CLAIM) {
                Alliance claimAlliance = event.getAlliance();

                if (currentControl == claimAlliance) continue;

                if (currentControl == Alliance.BLUE) {
                    blueControl += event.getTimestamp().getEpochSecond() - lastBlueControl;
                } else if (currentControl == Alliance.RED) {
                    redControl += event.getTimestamp().getEpochSecond() - lastRedControl;
                }

                currentControl = event.getAlliance();
                if (currentControl == Alliance.BLUE) {
                    lastBlueControl = event.getTimestamp().getEpochSecond();
                } else {
                    lastRedControl = event.getTimestamp().getEpochSecond();
                }
            } 
        }

        int blueBonusRP = blueControl >= controlRPThreshold ? 1 : 0;
        int redBonusRP = redControl >= controlRPThreshold ? 1 : 0;

        return Map.of(Alliance.BLUE, blueBonusRP, Alliance.RED, redBonusRP);
    }

    public Map<Alliance, Integer> calculatePushRP(Long matchID) {
        Match match = repository.findById(matchID).orElseThrow();

        List<ScoreEvent> events = match.getEvents();
        int bluePushes = 0, redPushes = 0;

        for (ScoreEvent event : events) {
            if (event.getEventType() == EventType.PUSH_HIGH_PLATFORM_TELE) {
                if (event.getAlliance() == Alliance.BLUE) {
                    bluePushes++;
                } else {
                    redPushes++;
                }
            }
        }

        int blueBonusRP = bluePushes >= pushRPThreshold ? 1 : 0;
        int redBonusRP = redPushes >= pushRPThreshold ? 1 : 0;

        return Map.of(Alliance.BLUE, blueBonusRP, Alliance.RED, redBonusRP);

    }

    public Map<Alliance, Integer> calculateTotalRP(Long matchID) {
        Map<Alliance, Integer> winRP = calculateWinRP(matchID);
        Map<Alliance, Integer> traversalRP = calculateTraversalRP(matchID);
        Map<Alliance, Integer> controlRP = calculateControlRP(matchID);
        Map<Alliance, Integer> pushRP = calculatePushRP(matchID);

        int blueTotal = winRP.get(Alliance.BLUE) + traversalRP.get(Alliance.BLUE) + controlRP.get(Alliance.BLUE) + pushRP.get(Alliance.BLUE);
        int redTotal = winRP.get(Alliance.RED) + traversalRP.get(Alliance.RED) + controlRP.get(Alliance.RED) + pushRP.get(Alliance.RED);

        return Map.of(Alliance.BLUE, blueTotal, Alliance.RED, redTotal);
    }


}