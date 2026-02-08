package demoanalyzer.com.dem.analyzer.internal.logic;

import demoanalyzer.com.dem.analyzer.api.model.Clutch;
import demoanalyzer.com.dem.analyzer.api.model.Entry;
import demoanalyzer.com.dem.analyzer.api.model.trade.Trade;
import demoanalyzer.com.dem.analyzer.api.model.PlayerStats;
import demoanalyzer.com.dem.parser.domain.model.CompleteMatchData;
import demoanalyzer.com.dem.parser.domain.model.raw.Kills;

import demoanalyzer.com.dem.parser.domain.model.stats.Adr;
import demoanalyzer.com.dem.parser.domain.model.stats.Kast;
import demoanalyzer.com.dem.parser.domain.model.stats.Rating;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PlayerStatsCalculator {

  public List<PlayerStats> calculate(
      CompleteMatchData matchData, List<Entry> entries, List<Clutch> clutches, List<Trade> trades) {
    Map<String, StatsAccumulator> accumulators = new HashMap<>();

    // 1. Inicjalizacja + Rating
    for (Rating s : matchData.ratings()) {
      if (isTotalStats(s.side())) {
        StatsAccumulator acc = new StatsAccumulator(s.name());
        // POPRAWKA: Pobieramy wartość double z obiektu StatsRating
        acc.rating = s.rating();
        accumulators.put(s.name(), acc);
      }
    }

    // 2. Uzupełnienie ADR
    for (Adr s : matchData.adrs()) {
      if (isTotalStats(s.side()) && accumulators.containsKey(s.name())) {
        // POPRAWKA: Pobieramy wartość double z obiektu StatsAdr
        accumulators.get(s.name()).adr = s.adr();
      }
    }

    // 3. Uzupełnienie KAST
    for (Kast s : matchData.kasts()) {
      if (isTotalStats(s.side()) && accumulators.containsKey(s.name())) {
        // POPRAWKA: Pobieramy wartość double z obiektu StatsKast
        accumulators.get(s.name()).kast = s.kast();
      }
    }

    // 4. Kills & Deaths
    for (Kills kill : matchData.kills()) {
      if (kill.attackerName() != null && accumulators.containsKey(kill.attackerName())) {
        accumulators.get(kill.attackerName()).kills++;
      }
      if (kill.victimName() != null && accumulators.containsKey(kill.victimName())) {
        accumulators.get(kill.victimName()).deaths++;
      }
    }

    // 5. Entry Frags (POPRAWIONE)
    for (Entry entry : entries) {
      // Używamy metody wonAfterEntry() zgodnie z definicją rekordu Entry
      if (entry.wonAfterEntry() && accumulators.containsKey(entry.fraggerName())) {
        accumulators.get(entry.fraggerName()).entryKills++;
      }
    }

    // 6. Clutche
    for (Clutch clutch : clutches) {
      if (accumulators.containsKey(clutch.clutcherName())) {
        accumulators.get(clutch.clutcherName()).clutchesWon++;
      }
    }

    // 7. Trade'y
    for (Trade trade : trades) {
      String traderName = trade.tradeKill().attackerName();
      if (accumulators.containsKey(traderName)) {
        accumulators.get(traderName).totalTrades++;
      }
    }

    // 8. Mapowanie do immutable DTO
    return accumulators.values().stream().map(this::mapToPlayerStats).toList();
  }

  private PlayerStats mapToPlayerStats(StatsAccumulator acc) {
    return PlayerStats.builder()
        .playerName(acc.playerName)
        .kills(acc.kills)
        .deaths(acc.deaths)
        .rating(acc.rating)
        .adr(acc.adr)
        .kast(acc.kast)
        .entryKills(acc.entryKills)
        .clutchesWon(acc.clutchesWon)
        .totalTrades(acc.totalTrades)
        .build();
  }

  private boolean isTotalStats(String side) {
    return side != null && ("ALL".equalsIgnoreCase(side) || "total".equalsIgnoreCase(side));
  }

  // Klasa pomocnicza (mutowalna)
  private static class StatsAccumulator {
    final String playerName;
    int kills = 0;
    int deaths = 0;

    // Pola jako proste typy double, a nie obiekty StatsAdr/StatsRating
    double rating = 0.0;
    double adr = 0.0;
    double kast = 0.0;

    int entryKills = 0;
    int clutchesWon = 0;
    int totalTrades = 0;

    public StatsAccumulator(String playerName) {
      this.playerName = playerName;
    }
  }
}
