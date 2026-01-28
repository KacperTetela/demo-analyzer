package demoanalyzer.com.dem.parser.domain.model;

import demoanalyzer.com.dem.parser.domain.model.header.Header;
import demoanalyzer.com.dem.parser.domain.model.raw.*;
import demoanalyzer.com.dem.parser.domain.model.stats.Adr;
import demoanalyzer.com.dem.parser.domain.model.stats.Kast;
import demoanalyzer.com.dem.parser.domain.model.stats.Rating;

import java.util.List;

public record CompleteMatchData(
    Header header,
    List<Bomb> bombs,
    List<Damages> damages,
    List<Footsteps> footsteps,
    List<GameInfo> gameInfos,
    List<Grenades> grenades,
    List<Infernos> infernos,
    List<Kills> kills,
    List<Rounds> rounds,
    List<Shots> shots,
    List<Smokes> smokes,
    List<Ticks> ticks,
    List<Adr> adrs,
    List<Kast> kasts,
    List<Rating> ratings) {}
