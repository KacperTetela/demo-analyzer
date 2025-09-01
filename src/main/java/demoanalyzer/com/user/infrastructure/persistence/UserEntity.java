package demoanalyzer.com.user.infrastructure.persistence;

import demoanalyzer.com.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_user")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull private String username;

  @NonNull private String password;

  static UserEntity from(User user) {
    return new UserEntity(user.id(), user.username(), user.password());
  }
}
