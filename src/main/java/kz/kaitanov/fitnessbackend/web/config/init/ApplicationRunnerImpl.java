package kz.kaitanov.fitnessbackend.web.config.init;

import kz.kaitanov.fitnessbackend.model.Exercise;
import kz.kaitanov.fitnessbackend.model.Menu;
import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.Role;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.enums.Area;
import kz.kaitanov.fitnessbackend.model.enums.Category;
import kz.kaitanov.fitnessbackend.model.enums.ProgramType;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;
import kz.kaitanov.fitnessbackend.model.enums.WeekDay;
import kz.kaitanov.fitnessbackend.repository.model.ExerciseRepository;
import kz.kaitanov.fitnessbackend.repository.model.MenuRepository;
import kz.kaitanov.fitnessbackend.repository.model.ProductRepository;
import kz.kaitanov.fitnessbackend.repository.model.RecipeRepository;
import kz.kaitanov.fitnessbackend.repository.model.SubMenuRepository;
import kz.kaitanov.fitnessbackend.repository.model.UserRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
@Profile("main")
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;
    private final SubMenuRepository subMenuRepository;
    private final MenuRepository menuRepository;
    private final ExerciseRepository exerciseRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        Role adminRole = new Role();
        Role userRole = new Role();
        adminRole.setName(RoleName.ADMIN);
        userRole.setName(RoleName.USER);
        adminRole = roleService.save(adminRole);
        userRole = roleService.save(userRole);

        User user;
        for (int i = 1; i < 6; i++) {
            user = new User();
            user.setUsername("user" + i);
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole(userRole);
            saveUser(user);
        }
        User admin;
        for (int i = 1; i < 6; i++) {
            admin = new User();
            admin.setUsername("admin" + i);
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole(adminRole);
            saveUser(admin);
        }

        Random random = new Random();

        String[] productNames = new String[] {"Onion", "Carrot", "Bread", "Potato", "Milk", "Cucumber", "Apple", "Banana",
                                              "Tomato", "Chicken", "Pork", "Beef", "Eggs", "Rice", "Beans", "Pasta",
                                              "Buckwheat", "Strawberry", "Pineapple", "Cheese"};
        Product product;
        for (String name : productNames) {
            product = new Product();
            product.setName(name);
            product.setCalorie(random.nextInt(100));
            product.setProtein(random.nextInt(50));
            product.setFat(random.nextInt(10));
            product.setCarbohydrate(random.nextInt(25));
            saveProduct(product);
        }

        String[] recipeNames = new String[] {"Onion rings", "Carrot sticks", "Toasts", "Mashed potato", "Protein cocktail",
                                             "Salad with cucumber", "Charlotte", "Banana pancakes", "Salad with tomato",
                                             "Chicken soup", "Burger with pork", "Steak with beef", "Scrambled eggs",
                                             "Risotto", "Leccio", "Carbonara", "Goulash with buckwheat", "Strawberry cake",
                                             "Pizza with pineapple", "Sandwich"};
        Recipe recipe;
        for (int i = 0; i < recipeNames.length; i++) {
            recipe = new Recipe();
            recipe.setCalorie(random.nextInt(1000, 3800));
            recipe.setCarbohydrate(random.nextInt(100, 200));
            recipe.setDescription("With " + productNames[i]);
            recipe.setFat(random.nextInt(50, 400));
            recipe.setName(recipeNames[i]);
            recipe.setProtein(random.nextInt(200, 500));
            saveRecipe(recipe);
        }

        SubMenu subMenu;
        WeekDay[] weekDays = WeekDay.values();
        for (int i = 0; i < 5; i++) {
            subMenu = new SubMenu();
            subMenu.setProgramType(ProgramType.WEIGHT_GAIN);
            subMenu.setWeekDay(weekDays[i]);
            saveSubMenu(subMenu);
        }

        Menu menu = new Menu();
        menu.setProgramType(ProgramType.WEIGHT_GAIN);
        saveMenu(menu);

        String[] exerciseNames = new String[] {"Running", "Sit ups", "Burpee", "Leg press", "Push ups", "Bench press",
                                               "Pull ups", "Push ups from the bench", "Biceps dumbbell curl",
                                               "Seated dumbbell press"};
        Exercise exercise;
        List<String> muscleGroup;
        for (int i = 0; i < exerciseNames.length; i++) {
            exercise = new Exercise();
            exercise.setName(exerciseNames[i]);
            exercise.setApproachCount(3);
            exercise.setRepeatCount(10);
            if (exercise.getName().equals("Running") || exercise.getName().equals("Burpee")) {
                exercise.setCategory(Category.CARDIO);
            } else {
                exercise.setCategory(Category.STRENGTH);
            }
            if (i >= 0 && i <= 3) {
                exercise.setArea(Area.LEGS);
            } else if (i > 3 && i <= 5) {
                exercise.setArea(Area.CHEST);
            } else if (i == 6) {
                exercise.setArea(Area.BREAST);
            } else {
                exercise.setArea(Area.ARMS);
            }
            muscleGroup = new ArrayList<>();
            if (exercise.getArea().equals(Area.LEGS)) {
                muscleGroup.add("Quadriceps");
                muscleGroup.add("Biceps femoris");
                exercise.setMuscleGroups(muscleGroup);
            } else if (exercise.getArea().equals(Area.CHEST)) {
                muscleGroup.add("Chest");
                exercise.setMuscleGroups(muscleGroup);
            } else if (exercise.getArea().equals(Area.BREAST)) {
                muscleGroup.add("Lats muscle");
                exercise.setMuscleGroups(muscleGroup);
            } else if (exercise.getArea().equals(Area.ARMS)) {
                muscleGroup.add("Triceps");
                muscleGroup.add("Biceps");
                muscleGroup.add("Shoulders");
                exercise.setMuscleGroups(muscleGroup);
            }
            saveExercise(exercise);
        }
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    @Transactional
    public void saveSubMenu(SubMenu subMenu) {
        subMenuRepository.save(subMenu);
    }

    @Transactional
    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }

    @Transactional
    public void saveExercise(Exercise exercise) {
        exerciseRepository.save(exercise);
    }
}
