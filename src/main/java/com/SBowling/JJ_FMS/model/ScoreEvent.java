package com.SBowling.JJ_FMS.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class ScoreEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Alliance alliance;

  @Enumerated(EnumType.STRING)
  private EventType eventType;

  private Instant timestamp;

  private int points;

  public ScoreEvent() {}

  public ScoreEvent(Alliance alliance, EventType eventType) {
    this.alliance = alliance;
    this.eventType = eventType;
    this.points = eventType.getPointValue();
    this.timestamp = Instant.now();
  }

  public Alliance getAlliance() {
    return alliance;
  }

  public int getPoints() {
    return points;
  }
}
