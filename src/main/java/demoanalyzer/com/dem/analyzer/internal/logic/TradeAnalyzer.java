package demoanalyzer.com.dem.analyzer.internal.logic;

import demoanalyzer.com.dem.analyzer.internal.model.trade.Trade;
import demoanalyzer.com.dem.analyzer.internal.model.trade.TradeType;
import demoanalyzer.com.dem.parser.domain.model.raw.Kills;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class TradeAnalyzer {

  // Stałe przenieś najlepiej do konfiguracji lub zostaw tutaj
  private static final int INSTANT_TRADE_TICK_LIMIT = 128; // np. 2 sekundy (64 tick)
  private static final int LATE_TICK_LIMIT = 320; // np. 5 sekund

  public List<Trade> analyze(List<Kills> kills) {
    List<Trade> trades = new ArrayList<>();

    // Sortowanie kluczowe dla algorytmu
    List<Kills> sortedKills = new ArrayList<>(kills);
    sortedKills.sort(Comparator.comparingLong(Kills::tick));

    for (int i = 0; i < sortedKills.size() - 1; i++) {
      Kills firstKill = sortedKills.get(i);

      for (int j = i + 1; j < sortedKills.size(); j++) {
        Kills potentialTrade = sortedKills.get(j);

        // 1. Sprawdź czas
        long tickDiff = potentialTrade.tick() - firstKill.tick();
        if (tickDiff > LATE_TICK_LIMIT) break; // Za późno na trade

        // 2. Logika Trade'a:
        // Zabójca ofiary (A) zostaje zabity przez kolegę ofiary (B).
        // Czyli: potentialTrade.victim == firstKill.attacker
        if (potentialTrade.victimName().equals(firstKill.attackerName())) {

          // Sprawdź czy to nie TeamKill przy okazji
          if (isSameTeam(potentialTrade.attackerSide(), firstKill.victimSide())) {

            TradeType type =
                (tickDiff <= INSTANT_TRADE_TICK_LIMIT)
                    ? TradeType.INSTANT_TRADE
                    : TradeType.LATE_TRADE;

            trades.add(new Trade(firstKill, potentialTrade, type));
            break; // Znaleziono trade, idziemy do kolejnego fraga
          }
        }
      }
    }
    return trades;
  }

  private boolean isSameTeam(String sideA, String sideB) {
    return sideA != null && sideA.equalsIgnoreCase(sideB);
  }
}
