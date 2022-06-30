package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.Menu;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import kz.kaitanov.fitnessbackend.repository.model.MenuRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.MenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImp extends AbstractServiceImpl<Menu, Long> implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImp(MenuRepository menuRepository) {
        super(menuRepository);
        this.menuRepository = menuRepository;
    }

    @Override
    public Page<Menu> findAll(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    @Override
    public Menu addSubMenusToMenu(Menu menu, List<SubMenu> subMenuList) {
        for (SubMenu submenu : subMenuList) {
            menu.addSubMenu(submenu);
        }
        return update(menu);
    }

    @Override
    public Menu deleteSubMenusToMenu(Menu menu, List<SubMenu> subMenuList) {
        for (SubMenu submenu : subMenuList) {
            menu.deleteSubMenu(submenu);
        }
        return update(menu);
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
