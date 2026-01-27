package demoanalyzer.com.dem.persistence.entity.header;

import demoanalyzer.com.dem.domain.model.header.Header;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeaderEntity {
  private String mapName;
  private String serverName;
}
