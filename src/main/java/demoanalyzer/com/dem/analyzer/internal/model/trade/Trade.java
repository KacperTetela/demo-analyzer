package demoanalyzer.com.dem.analyzer.internal.model.trade;


import demoanalyzer.com.dem.parser.domain.model.raw.Kills;

public record Trade(Kills currentKill, Kills tradeKill, TradeType tradeType) {}
