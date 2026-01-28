package demoanalyzer.com.legacy.replay;

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
        Field field = DomainReplayServiceAdapter.class.getDeclaredField("gameplayDeserializer");
        field.setAccessible(true);
        field.set(replayService, gameplayDeserializer);*//*

    }

*/
/*    @Test
    void testGetBasicReplayInfo() {
        // Arrange
        Header headerEvent = new Header("Test Map", "Test Server");
        replayService.setTestHeaderEvent(headerEvent);

        // Act
        GameDetails result = replayService.getBasicReplayInfo();

        // Assert
        assertNotNull(result);
        assertEquals("Test Map", result.mapName());
        assertEquals("Test Server", result.serverName());
    }

    *//*
*/
/**
     * Rozszerzona klasa DomainReplayServiceAdapter dla celów testowych, pozwalająca
     * kontrolować zwracane wyniki z HeaderDeserializer
     *//*
*/
/*
    private static class TestableReplayService extends DomainReplayServiceAdapter {
        private Header testHeaderEvent;

        public void setTestHeaderEvent(Header testHeaderEvent) {
            this.testHeaderEvent = testHeaderEvent;
        }

        @Override
        public GameDetails getBasicReplayInfo() {
            // Dla celów testowych, pomijamy rzeczywistą implementację HeaderDeserializer
            if (testHeaderEvent != null) {
                return new GameDetails(testHeaderEvent.map_name(), testHeaderEvent.server_name());
            }
            return super.getBasicReplayInfo();
        }
    }*//*


    // Klasy pomocnicze do testów
    private static class Header {
        private final String map_name;
        private final String server_name;

        public Header(String map_name, String server_name) {
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
