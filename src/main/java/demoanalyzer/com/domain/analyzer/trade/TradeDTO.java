package demoanalyzer.com.domain.analyzer.trade;

import demoanalyzer.com.domain.replay.conversion.gameplay.KillsEvent;

public record TradeDTO(KillsEvent currentKill, KillsEvent tradeKill) {}
