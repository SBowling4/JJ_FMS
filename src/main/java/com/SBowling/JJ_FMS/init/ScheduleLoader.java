package com.SBowling.JJ_FMS.init;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.SBowling.JJ_FMS.model.Match;
import com.SBowling.JJ_FMS.model.Team;
import com.SBowling.JJ_FMS.repository.MatchRepository;
import com.SBowling.JJ_FMS.repository.TeamRepository;

@Component
public class ScheduleLoader implements CommandLineRunner {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public ScheduleLoader(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (matchRepository.count() > 0) return; // Don't overwrite existing schedule

        // Assume teams are already in DB as Team 1 -> Team 8 with IDs 1..8
        Team t1 = teamRepository.findById(1).orElseThrow();
        Team t2 = teamRepository.findById(2).orElseThrow();
        Team t3 = teamRepository.findById(3).orElseThrow();
        Team t4 = teamRepository.findById(4).orElseThrow();
        Team t5 = teamRepository.findById(5).orElseThrow();
        Team t6 = teamRepository.findById(6).orElseThrow();
        Team t7 = teamRepository.findById(7).orElseThrow();
        Team t8 = teamRepository.findById(8).orElseThrow();

        // Create matches
        Match m1  = new Match(t1, t2, t3, t4);
        Match m2  = new Match(t3, t5, t1, t6);
        Match m3  = new Match(t4, t6, t2, t7);
        Match m4  = new Match(t7, t8, t3, t1);
        Match m5  = new Match(t2, t5, t4, t8);
        Match m6  = new Match(t6, t1, t5, t7);
        Match m7  = new Match(t3, t8, t2, t6);
        Match m8  = new Match(t4, t7, t1, t5);
        Match m9  = new Match(t2, t3, t6, t8);
        Match m10 = new Match(t1, t4, t5, t7);
        Match m11 = new Match(t6, t2, t3, t8);
        Match m12 = new Match(t7, t1, t4, t5);

        matchRepository.saveAll(List.of(
            m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12
        ));
    }
}