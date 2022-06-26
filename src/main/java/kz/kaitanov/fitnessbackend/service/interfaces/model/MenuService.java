package kz.kaitanov.fitnessbackend.service.interfaces.model;


import kz.kaitanov.fitnessbackend.model.Menu;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MenuService extends AbstractService<Menu, Long> {

    Menu addSubMenuToMenu(Menu menu, SubMenu subMenu);

    Menu deleteSubMenuFromMenu(Menu menu, SubMenu subMenu);

    Optional<Menu> findByIdWithSubMenus(Long id);

    Page<Menu> findAll(Pageable pageable);
}
