package demoanalyzer.com.domain.replay;

import demoanalyzer.com.domain.analyzer.GameDetailsDTO;
import demoanalyzer.com.domain.replay.conversion.gameplay.GameplayDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Spy;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;

/*
@ExtendWith(MockitoExtension.class)
public class ReplayServiceTest {

    @Mock
    private GameplayDeserializer gameplayDeserializer;

*/
/*    @Spy
    private TestableReplayService replayService;*//*


*/
/*    @BeforeEach
    void setUp() throws Exception {
        // Inicjalizacja mocków
        MockitoAnnotations.openMocks(this);

        // Wstrzyknięcie mockowanego GameplayDeserializer przez refleksję
        Field field = ReplayService.class.getDeclaredField("gameplayDeserializer");
        field.setAccessible(true);
        field.set(replayService, gameplayDeserializer);*//*

    }

*/
/*    @Test
    void testGetBasicReplayInfo() {
        // Arrange
        HeaderEvent headerEvent = new HeaderEvent("Test Map", "Test Server");
        replayService.setTestHeaderEvent(headerEvent);

        // Act
        GameDetailsDTO result = replayService.getBasicReplayInfo();

        // Assert
        assertNotNull(result);
        assertEquals("Test Map", result.mapName());
        assertEquals("Test Server", result.serverName());
    }

    *//*
*/
/**
     * Rozszerzona klasa ReplayService dla celów testowych, pozwalająca
     * kontrolować zwracane wyniki z HeaderDeserializer
     *//*
*/
/*
    private static class TestableReplayService extends ReplayService {
        private HeaderEvent testHeaderEvent;

        public void setTestHeaderEvent(HeaderEvent testHeaderEvent) {
            this.testHeaderEvent = testHeaderEvent;
        }

        @Override
        public GameDetailsDTO getBasicReplayInfo() {
            // Dla celów testowych, pomijamy rzeczywistą implementację HeaderDeserializer
            if (testHeaderEvent != null) {
                return new GameDetailsDTO(testHeaderEvent.map_name(), testHeaderEvent.server_name());
            }
            return super.getBasicReplayInfo();
        }
    }*//*


    // Klasy pomocnicze do testów
    private static class HeaderEvent {
        private final String map_name;
        private final String server_name;

        public HeaderEvent(String map_name, String server_name) {
            this.map_name = map_name;
            this.server_name = server_name;
        }

        public String map_name() {
            return map_name;
        }

        public String server_name() {
            return server_name;
        }
    }

    // Przykładowe implementacje potrzebnych klas do testów
    private interface GameplayEvent { }

    private static class KillEvent implements GameplayEvent {
        private final String killer;
        private final String victim;
        private final String weapon;

        public KillEvent(String killer, String victim, String weapon) {
            this.killer = killer;
            this.victim = victim;
            this.weapon = weapon;
        }
    }

    private static class MoveEvent implements GameplayEvent {
        private final String player;
        private final int x;
        private final int y;

        public MoveEvent(String player, int x, int y) {
            this.player = player;
            this.x = x;
            this.y = y;
        }
    }

    private static class BasicDTO {
        private final String mapName;
        private final String serverName;

        public BasicDTO(String mapName, String serverName) {
            this.mapName = mapName;
            this.serverName = serverName;
        }

        public String mapName() {
            return mapName;
        }

        public String serverName() {
            return serverName;
        }
    }
}*/
