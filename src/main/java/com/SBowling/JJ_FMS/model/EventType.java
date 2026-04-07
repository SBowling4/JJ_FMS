package com.SBowling.JJ_FMS.model;

import lombok.Getter;

public enum EventType {
  LEAVE(3, GamePeriod.AUTO),
  LOW_PLATFORM_AUTO(2, GamePeriod.AUTO),
  LOW_PLATFORM_TELEOP(1, GamePeriod.TELEOP),
  HIGH_PLATFORM_AUTO(3, GamePeriod.AUTO),
  HIGH_PLATFORM_TELEOP(2, GamePeriod.TELEOP),
  PUSH_LOW_PLATFORM_TELEOP(4, GamePeriod.TELEOP),
  PUSH_LOW_PLATFORM_AUTO(6, GamePeriod.AUTO),
  PUSH_HIGH_PLATFORM_AUTO(12, GamePeriod.AUTO),
  PUSH_HIGH_PLATFORM_TELE(8, GamePeriod.TELEOP),
  PARK(2, GamePeriod.ENDGAME),
  CLIMB(10, GamePeriod.ENDGAME),
  BUDDY_CLIMB(10, GamePeriod.ENDGAME),
  BANK_CLAIM(0, GamePeriod.TELEOP);

  @Getter private final int pointValue;
  @Getter private final GamePeriod period;

  private EventType(int pointValue, GamePeriod period) {
    this.pointValue = pointValue;
    this.period = period;
  }

  public static boolean isTraversal(EventType eventType) {
    return eventType == LEAVE || eventType == LOW_PLATFORM_AUTO || eventType == LOW_PLATFORM_TELEOP ||
           eventType == HIGH_PLATFORM_AUTO || eventType == HIGH_PLATFORM_TELEOP;
  }
}
