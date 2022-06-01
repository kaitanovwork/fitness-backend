package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;
import kz.kaitanov.fitnessbackend.repository.model.UserRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RoleService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName(RoleName.USER));
        return super.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User %s not found", username))
        );
    }

    @Override
    public User updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.update(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public Optional<User> findByIdWithRoles(Long id) {
        return userRepository.findByIdWithRoles(id);
    }
}
