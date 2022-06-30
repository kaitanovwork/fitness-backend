package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SubMenuService extends AbstractService<SubMenu, Long> {

    Page<SubMenu> findAll(Pageable pageable);

    SubMenu addRecipeToSubMenu(SubMenu subMenu, Recipe recipe);

    SubMenu deleteRecipeFromSubMenu(SubMenu subMenu, Recipe recipe);

    Page<SubMenu> findAll(Pageable pageable);

    Optional<SubMenu> findByIdWithRecipes(Long id);

    List<SubMenu> findByIds(Long[] ids);
}
