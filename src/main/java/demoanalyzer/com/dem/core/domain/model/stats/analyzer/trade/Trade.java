package demoanalyzer.com.dem.core.domain.model.stats.analyzer.trade;


import demoanalyzer.com.dem.parser.domain.model.raw.Kills;

public record Trade(Kills currentKill, Kills tradeKill, TradeType tradeType) {}
