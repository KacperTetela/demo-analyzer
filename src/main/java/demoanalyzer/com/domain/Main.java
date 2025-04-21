package demoanalyzer.com.domain;

import demoanalyzer.com.domain.replay.ReplayService;
import demoanalyzer.com.domain.replay.conversion.gameplay.DamagesEvent;
import demoanalyzer.com.domain.replay.conversion.gameplay.GameplayDeserializer;
import demoanalyzer.com.domain.replay.conversion.gameplay.GameplayEvent;
import demoanalyzer.com.domain.replay.conversion.gameplay.KillsEvent;

import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    /*    ParserHandler parserHandler = new ParserHandler();
    System.out.println(parserHandler.parse());*/

    ReplayService replayService = new ReplayService();

    // Pobierz tylko konkretny typ zdarzeń
    List<DamagesEvent> damages = replayService.getGameplayEvents(DamagesEvent.class);
    List<KillsEvent> kills = replayService.getGameplayEvents(KillsEvent.class);

    damages.forEach(System.out::println);
  }
}
