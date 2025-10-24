package demoanalyzer.com.securityLegacy.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class PasswordEncoderAdapter implements PasswordEncoder {
  private final org.springframework.security.crypto.password.PasswordEncoder delegate;

  @Override
  public String encode(CharSequence rawPassword) {
    return delegate.encode(rawPassword);
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return delegate.matches(rawPassword, encodedPassword);
  }
}
