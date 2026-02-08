package demoanalyzer.com.dem.core.service;

import demoanalyzer.com.dem.core.domain.model.stats.awpy.Side;
import demoanalyzer.com.dem.core.domain.model.stats.awpy.StatsAdr;
import demoanalyzer.com.dem.core.domain.model.stats.awpy.StatsKast;
import demoanalyzer.com.dem.core.domain.model.stats.awpy.StatsRating;
import demoanalyzer.com.dem.parser.domain.model.stats.Adr;
import demoanalyzer.com.dem.parser.domain.model.stats.Kast;
import demoanalyzer.com.dem.parser.domain.model.stats.Rating;
import org.springframework.stereotype.Component;

@Component
public class StatsMapper {

  // --- ADR MAPPING ---
  public StatsAdr mapToStatsAdr(Adr adr) {
    return new StatsAdr(
        adr.name(),
        adr.steamid(), // parser: steamid (małe i)
        mapSide(adr.side()), // konwersja String -> Enum
        adr.n_rounds(), // parser: n_rounds (snake_case)
        adr.dmg(),
        adr.adr());
  }

  // --- KAST MAPPING ---
  // Zakładam strukturę rekordu Kast z parsera analogicznie do Adr
  public StatsKast mapToStatsKast(Kast kast) {
    return new StatsKast(
        kast.name(),
        kast.steamid(), // parser: steamid
        mapSide(kast.side()),
        kast.n_rounds(), // parser: n_rounds
        kast.kast_rounds(), // parser: kast_rounds (snake_case)
        kast.kast());
  }

  // --- RATING MAPPING ---
  // Zakładam strukturę rekordu Rating z parsera
  public StatsRating mapToStatsRating(Rating rating) {
    return new StatsRating(
        rating.name(),
        rating.steamid(), // parser: steamid
        mapSide(rating.side()),
        rating.n_rounds(), // parser: n_rounds
        rating.impact(),
        rating.rating());
  }

  // --- HELPER ---
  private Side mapSide(String sideStr) {
    if (sideStr == null) return null;
    try {
      // Obsługa "CT", "TERRORIST" itp.
      return Side.valueOf(sideStr.toUpperCase());
    } catch (IllegalArgumentException e) {
      // Logowanie błędu opcjonalnie
      return null;
    }
  }
}
