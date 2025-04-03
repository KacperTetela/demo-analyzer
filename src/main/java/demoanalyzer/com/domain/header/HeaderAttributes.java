package demoanalyzer.com.domain.header;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HeaderAttributes(String map_name, String network_protocol, String server_name) {
}