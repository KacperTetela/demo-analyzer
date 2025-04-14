package demoanalyzer.com.domain.replay.conversion.header;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HeaderEvent(String map_name, String network_protocol, String server_name) {}
