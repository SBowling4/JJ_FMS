package com.SBowling.JJ_FMS.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.event.spi.EventType;
import org.springframework.stereotype.Service;

import com.SBowling.JJ_FMS.model.Match;
import com.SBowling.JJ_FMS.model.ScoreEvent;

@Service
public class RankingPointService {
    public int calculateWinRP(int allianceScore, int opponentScore) {
        if (allianceScore > opponentScore) {
            return 3;
        } else if (allianceScore == opponentScore) {
            return 1;
        } else {
            return 0;
        }
    }

    public int calculateBonusRP(Match match) {
        List<ScoreEvent> events = match.getEvents();

    }
}
