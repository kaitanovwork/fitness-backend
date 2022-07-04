package kz.kaitanov.fitnessbackend.web.config.init;

import kz.kaitanov.fitnessbackend.model.Role;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;
import kz.kaitanov.fitnessbackend.repository.model.UserRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
    //  Role adminRole = new Role();
    //  Role userRole = new Role();
    //  adminRole.setName(RoleName.ADMIN);
    //  userRole.setName(RoleName.USER);
    //  adminRole = roleService.save(adminRole);
    //  userRole = roleService.save(userRole);

    //  User user = new User();
    //  user.setUsername("user");
    //  user.setPassword(passwordEncoder.encode("password"));
    //  user.setRole(userRole);
    //  User admin = new User();
    //  admin.setUsername("admin");
    //  admin.setPassword(passwordEncoder.encode("password"));
    //  admin.setRole(adminRole);
    //  saveUser(user);
    //  saveUser(admin);
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
