package demoanalyzer.com.legacy.analyzer.trade;

import demoanalyzer.com.legacy.replay.conversion.gameplay.KillsEvent;

public record TradeDTO(KillsEvent currentKill, KillsEvent tradeKill, TradeType tradeType) {}
