package demoanalyzer.com.dem.legacy.analyzer.trade;

import demoanalyzer.com.dem.legacy.replay.conversion.gameplay.KillsEvent;

import java.util.ArrayList;
import java.util.List;

public class TradeAnalyzer {
  public List<TradeDTO> analyzeTrade(List<KillsEvent> killsEvents) {
    List<TradeDTO> trades = new ArrayList<>();

    for (int i = 0; i < killsEvents.size() - 1; i++) {
      KillsEvent firstKill = killsEvents.get(i);
      TradeType tradeType = TradeType.LATE_TRADE;

      for (int j = i + 1; j < killsEvents.size(); j++) {
        KillsEvent potentialTrade = killsEvents.get(j);

        // Jeżeli poza limitem czasu — przerwij pętlę
        if (potentialTrade.tick() - firstKill.tick() > TradeType.LATE_TICK_LIMIT) {
          break;
        }

        // Czy zabójca drugiego fraga zabił zabójcę pierwszego?
        if (potentialTrade.victimName().equals(firstKill.attackerName())) {

          if (!(potentialTrade.attackerSide() == null || firstKill.attackerSide() == null))

            // Czy ofiara pierwszego fraga i zabójca drugiego są po tej samej stronie
            if (potentialTrade.attackerSide().equalsIgnoreCase(firstKill.victimSide())) {

              // To jest trade frag! Czy Jest Instant
              if (potentialTrade.tick() - firstKill.tick() > TradeType.INSTANT_TRADE_TICK_LIMIT) {
                tradeType = TradeType.INSTANT_TRADE;
              }

              trades.add(new TradeDTO(firstKill, potentialTrade, tradeType));
              break; // zakładamy tylko 1 trade per frag
            }
        }
      }
    }

    return trades;
  }
}
