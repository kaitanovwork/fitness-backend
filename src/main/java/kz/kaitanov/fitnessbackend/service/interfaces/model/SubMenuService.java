package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SubMenuService extends AbstractService<SubMenu, Long> {

    SubMenu addRecipeToSubMenu(SubMenu subMenu, Recipe recipe);

    SubMenu deleteRecipeFromSubMenu(SubMenu subMenu, Recipe recipe);

    Page<SubMenu> findAll(Pageable pageable);

    Optional<SubMenu> findByIdWithRecipes(Long id);
}
