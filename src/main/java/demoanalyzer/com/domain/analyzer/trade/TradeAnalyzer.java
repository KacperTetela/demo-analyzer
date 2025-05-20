package demoanalyzer.com.domain.analyzer.trade;

import demoanalyzer.com.domain.replay.conversion.gameplay.KillsEvent;

import java.util.ArrayList;
import java.util.List;

public class TradeAnalyzer {
  public List<TradeDTO> analyzeTrade(List<KillsEvent> killsEvents) {
    List<TradeDTO> trades = new ArrayList<>();

    for (int i = 0; i < killsEvents.size() - 1; i++) {
      KillsEvent currentKill = killsEvents.get(i);
      KillsEvent tradeKill = killsEvents.get(i + 1);

      if (tradeKill.tick() - currentKill.tick() <= 640) {
        trades.add(new TradeDTO(currentKill, tradeKill));
      }
    }

    return trades;
  }
}
