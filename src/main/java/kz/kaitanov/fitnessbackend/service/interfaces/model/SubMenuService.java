package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.SubMenu;

import java.util.List;
import java.util.Optional;

public interface SubMenuService extends AbstractService<SubMenu, Long> {

    SubMenu addRecipeToSubMenu(SubMenu subMenu, Recipe recipe);

    SubMenu deleteRecipeFromSubMenu(SubMenu subMenu, Recipe recipe);

    Optional<SubMenu> findByIdWithRecipes(Long id);

    List<SubMenu> findByIds(Long[] ids);
}
