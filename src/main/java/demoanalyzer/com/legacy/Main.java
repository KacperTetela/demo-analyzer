package demoanalyzer.com.legacy;

import demoanalyzer.com.legacy.replay.DomainReplayService;
import demoanalyzer.com.legacy.replay.conversion.raw.DamagesEvent;
import demoanalyzer.com.legacy.replay.conversion.raw.KillsEvent;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    /*    ParserHandler parserHandler = new ParserHandler();
    System.out.println(parserHandler.parse());*/

    DomainReplayService domainReplayService = new DomainReplayService();

    // Pobierz tylko konkretny typ zdarzeń
    List<DamagesEvent> damages = domainReplayService.getGameplayEvents(DamagesEvent.class);
    List<KillsEvent> kills = domainReplayService.getGameplayEvents(KillsEvent.class);

    damages.forEach(System.out::println);
  }
}
