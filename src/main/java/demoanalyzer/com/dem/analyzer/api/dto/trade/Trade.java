package demoanalyzer.com.dem.analyzer.api.dto.trade;


import demoanalyzer.com.dem.parser.domain.model.raw.Kills;

public record Trade(Kills currentKill, Kills tradeKill, TradeType tradeType) {}
