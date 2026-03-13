package com.SBowling.JJ_FMS.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SBowling.JJ_FMS.model.*;
import com.SBowling.JJ_FMS.service.ScoringService;

@RestController
@RequestMapping("/matches")
public class MatchController {
    private final ScoringService service;

    public MatchController(ScoringService service) {
        this.service = service;
    }

    @PostMapping
    public Match createMatch(@RequestParam int b1, @RequestParam int b2, @RequestParam int r1, @RequestParam int r2) {
        return service.createMatch(b1, b2, r1, r2);
    }

    @PostMapping("{id}/events")
    public Match addEvent(@PathVariable Long id, @RequestParam Alliance alliance, @RequestParam EventType type) {
        return service.addEvent(id, alliance, type);
    }

    @PostMapping("{id}/score")
    public Map<String, Integer> getScore(@PathVariable Long id) {
        return service.getScore(id);
    }
    
}
