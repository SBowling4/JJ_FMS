package com.SBowling.JJ_FMS.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Match {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int blueAlliance1;
  private int blueAlliance2;

  private int redAlliance1;
  private int redAlliance2;

  @OneToMany(cascade = CascadeType.ALL)
  private List<ScoreEvent> events = new ArrayList<>();

  public Match(int b1, int b2, int r1, int r2) {
    this.blueAlliance1 = b1;
    this.blueAlliance2 = b2;

    this.redAlliance1 = r1;
    this.redAlliance2 = r2;
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
