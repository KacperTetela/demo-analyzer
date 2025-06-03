package demoanalyzer.com.domain.analyzer.trade;

public enum TradeType {
  INSTANT_TRADE,
  LATE_TRADE;

  final static int INSTANT_TRADE_TICK_LIMIT = 128;
  final static int LATE_TICK_LIMIT = 640;
}
