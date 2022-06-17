package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.Menu;
import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import kz.kaitanov.fitnessbackend.repository.model.MenuRepository;
import kz.kaitanov.fitnessbackend.repository.model.RecipeRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.MenuService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RecipeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuServiceImp extends AbstractServiceImpl<Menu, Long> implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImp(MenuRepository menuRepository) {
        super(menuRepository);
        this.menuRepository = menuRepository ;
    }


    @Override
    public boolean existsById(Long id) {
        return menuRepository.existsById(id);
    }

    @Override
    public Menu addSubMenuToMenu(Menu menu, SubMenu subMenu) {
        menu.addSubMenu(subMenu);
        return update(menu);
    }

    @Override
    public Menu deleteSubMenuFromMenu(Menu menu, SubMenu subMenu) {
        menu.deleteSubMenu(subMenu);
        return update(menu);
    }

    @Override
    public Optional<Menu> findByIdWithSubMenus(Long id) {
        return menuRepository.findByIdWithSubMenus(id);
    }
}
