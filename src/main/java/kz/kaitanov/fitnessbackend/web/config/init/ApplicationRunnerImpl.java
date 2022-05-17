package kz.kaitanov.fitnessbackend.web.config.init;

import kz.kaitanov.fitnessbackend.model.Role;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ExerciseService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RecipeService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RoleService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final ExerciseService exerciseService;
    private final RecipeService recipeService;

    @Override
    public void run(ApplicationArguments args) {
        Role adminRole = new Role();
        Role userRole = new Role();
        adminRole.setName(RoleName.ADMIN);
        userRole.setName(RoleName.USER);
        roleService.save(adminRole);
        roleService.save(userRole);

        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass");
        userService.save(user);
    }
}
