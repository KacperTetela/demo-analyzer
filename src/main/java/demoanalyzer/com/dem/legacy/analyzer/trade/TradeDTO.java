package demoanalyzer.com.dem.legacy.analyzer.trade;

import demoanalyzer.com.dem.legacy.replay.conversion.gameplay.KillsEvent;

public record TradeDTO(KillsEvent currentKill, KillsEvent tradeKill, TradeType tradeType) {}
