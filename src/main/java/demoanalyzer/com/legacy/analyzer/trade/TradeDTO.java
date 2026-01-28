package demoanalyzer.com.legacy.analyzer.trade;

import demoanalyzer.com.dem.parser.domain.model.raw.Kills;

public record TradeDTO(Kills currentKill, Kills tradeKill, TradeType tradeType) {}
