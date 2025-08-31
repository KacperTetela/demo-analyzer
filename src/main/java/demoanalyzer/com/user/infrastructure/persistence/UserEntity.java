package demoanalyzer.com.user.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_user")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull private String username;

  @NonNull private String password;
}
