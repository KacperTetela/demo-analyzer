package demoanalyzer.com.domain.user;

public interface UserProfileService {
  User updateProfileUser(String userId, User updatedUser);

  void changePasswordUser(String userId, String oldPassword, String newPassword);

  void deleteAccountUser(String userId);
}
