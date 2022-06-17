package kz.kaitanov.fitnessbackend.service.interfaces.model;


import kz.kaitanov.fitnessbackend.model.Menu;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import kz.kaitanov.fitnessbackend.service.implementations.model.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface MenuService extends AbstractService<Menu, Long> {

    boolean existsById(Long id);

    Menu addSubMenuToMenu(Menu menu, SubMenu subMenu);

    Menu deleteSubMenuFromMenu(Menu menu, SubMenu subMenu);

    Optional<Menu> findByIdWithSubMenus(Long id);
}
