package com.SBowling.JJ_FMS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Match {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter private final Team blueAlliance1;
  @Getter private final Team blueAlliance2;

  @Getter private final Team redAlliance1;
  @Getter private final Team redAlliance2;

  @Getter @Setter private boolean finalized = false;
  @Getter @Setter private boolean rankingsApplied = false;
  @Getter @Setter private boolean teamChangesApplied = false;

  @OneToMany(cascade = CascadeType.ALL)
  private List<ScoreEvent> events = new ArrayList<>();

  public Match() {
    blueAlliance1 = null;
    blueAlliance2 = null;
    redAlliance1 = null;
    redAlliance2 = null;
  }

  public Match(Team b1, Team b2, Team r1, Team r2) {
    this.blueAlliance1 = b1;
    this.blueAlliance2 = b2;

    this.redAlliance1 = r1;
    this.redAlliance2 = r2;
  }

  public Match(MatchAlliance blue, MatchAlliance red) {
    this.blueAlliance1 = blue.getTeam1();
    this.blueAlliance2 = blue.getTeam2();

    this.redAlliance1 = red.getTeam1();
    this.redAlliance2 = red.getTeam2();
  }

  public Long getId() {
    return id;
  }

  public List<ScoreEvent> getEvents() {
    return events;
  }

  public void addEvent(ScoreEvent event) {
    events.add(event);
  }
}