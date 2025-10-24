package demoanalyzer.com.user.auth.persistence;

import demoanalyzer.com.user.auth.domain.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auth_user")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthUserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull private String email;

  @NonNull private String password;

  static AuthUserEntity from(User user) {
    return new AuthUserEntity(user.id(), user.email(), user.password());
  }
}
