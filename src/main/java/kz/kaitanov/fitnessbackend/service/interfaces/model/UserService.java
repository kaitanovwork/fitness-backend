package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends AbstractService<User, Long>, UserDetailsService {

    User updatePassword(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<User> findByIdWithRoles(Long id);

    User addCoach(User user, User coach);
}
