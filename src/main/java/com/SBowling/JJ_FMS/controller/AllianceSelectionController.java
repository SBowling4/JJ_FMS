package com.SBowling.JJ_FMS.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SBowling.JJ_FMS.service.AllianceSelectionService;

@RestController
@RequestMapping("/alliances")
public class AllianceSelectionController {
    private final AllianceSelectionService allianceSelectionService;

    public AllianceSelectionController(AllianceSelectionService allianceSelectionService) {
        this.allianceSelectionService = allianceSelectionService;
    }

    @PostMapping("/create")
    public void createAlliance(@RequestParam int team1, @RequestParam int team2) {
        allianceSelectionService.createAlliance(team1, team2);
    }
}
