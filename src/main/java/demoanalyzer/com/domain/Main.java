package demoanalyzer.com.domain;

import demoanalyzer.com.domain.replay.conversion.gameplay.GameplayDeserializer;

public class Main {
  public static void main(String[] args) {
/*    ParserHandler parserHandler = new ParserHandler();
    System.out.println(parserHandler.parse());*/

    /*        HeaderDeserializer headerDeserializer = new HeaderDeserializer();
    System.out.println(headerDeserializer.deserialize());*/

    GameplayDeserializer gameplayDeserializer = new GameplayDeserializer();
    gameplayDeserializer.processAllCsvFiles();
  }
}
