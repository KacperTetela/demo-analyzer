package demoanalyzer.com.domain;

import demoanalyzer.com.domain.replay.conversion.header.HeaderDeserializer;
import demoanalyzer.com.domain.replay.parserhandler.ParserHandler;

public class Main {
  public static void main(String[] args) {
    ParserHandler parserHandler = new ParserHandler();
    System.out.println(parserHandler.parse());

    /*        HeaderDeserializer headerDeserializer = new HeaderDeserializer();
    System.out.println(headerDeserializer.deserialize());*/
  }
}
