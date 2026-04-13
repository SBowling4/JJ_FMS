package com.SBowling.JJ_FMS.init;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.SBowling.JJ_FMS.model.Team;
import com.SBowling.JJ_FMS.repository.TeamRepository;

@Component
public class TeamLoader implements CommandLineRunner {

    private final TeamRepository teamRepository;

    public TeamLoader(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (teamRepository.count() == 0) {
            teamRepository.saveAll(
                List.of(new Team(1, "Team 1"), new Team(2, "Team 2"), new Team(3, "Team 3"), new Team(4, "Team 4"), new Team(5, "Team 5"), new Team(6, "Team 6"), new Team(7, "Team 7"), new Team(8, "Team 8"))
            );
        }
    }
}
