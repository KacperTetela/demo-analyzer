package demoanalyzer.com.domain.header;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class HeaderDeserializer {


    public Optional<HeaderAttributes> deserialize() {
        String json = """
                {
                    "client_name": "SourceTV Demo",
                    "demo_version_name": "valve_demo_2",
                    "addons": "",
                    "game_directory": "/home/steam/cs2/game/csgo",
                    "allow_clientside_entities": true,
                    "allow_clientside_particles": true,
                    "demo_file_stamp": "PBDEMS2\\\\x00",
                    "fullpackets_version": "2",
                    "server_name": "BLAST Bounty CS2 Server",
                    "network_protocol": "14072",
                    "map_name": "de_dust2",
                    "demo_version_guid": "8e9d71ab-04a1-4c01-bb61-acfede27c046"
                }
                """;

        ObjectMapper mapper = new ObjectMapper();
        try {
            HeaderAttributes attributes = mapper.readValue(json, HeaderAttributes.class);
            return Optional.of(attributes);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


}
