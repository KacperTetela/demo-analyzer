package demoanalyzer.com.dem.parser.domain.model.header;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Header(String map_name, String network_protocol, String server_name) {}
