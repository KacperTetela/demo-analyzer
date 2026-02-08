package demoanalyzer.com.dem.analyzer.api.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class PlayerStats {

  private final String playerName;

  // --- Podstawowe (K/D) ---
  private final int kills;
  private final int deaths;

  // --- Zaawansowane ---
  private final double rating;
  private final double adr; // Average Damage per Round
  private final double kast; // Kill, Assist, Survive, Trade %

  // --- Zdarzeniowe (Impact) ---
  private final int entryKills; // Otwarcia
  private final int clutchesWon; // Wygrane 1vsX
  private final int totalTrades; // Wymiany
}
