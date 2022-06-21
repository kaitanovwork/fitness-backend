package kz.kaitanov.fitnessbackend.service.interfaces.model;


import kz.kaitanov.fitnessbackend.model.Menu;
import kz.kaitanov.fitnessbackend.model.SubMenu;

import java.util.Optional;

public interface MenuService extends AbstractService<Menu, Long> {

    Menu addSubMenuToMenu(Menu menu, SubMenu subMenu);

    Menu deleteSubMenuFromMenu(Menu menu, SubMenu subMenu);

    Optional<Menu> findByIdWithSubMenus(Long id);
}
