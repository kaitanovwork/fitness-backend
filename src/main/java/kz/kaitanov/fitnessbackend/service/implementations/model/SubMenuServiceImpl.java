package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import kz.kaitanov.fitnessbackend.repository.model.SubMenuRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.SubMenuService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubMenuServiceImpl extends AbstractServiceImpl<SubMenu, Long> implements SubMenuService {

    SubMenuRepository subMenuRepository;

    public SubMenuServiceImpl (SubMenuRepository subMenuRepository) {
        super(subMenuRepository);
        this.subMenuRepository = subMenuRepository;
    }

    public SubMenu addRecipeToSubMenu (SubMenu subMenu, Recipe recipe) {
        subMenu.addRecipe(recipe);
        return update(subMenu);
    }

    public SubMenu deleteRecipeFromSubMenu (SubMenu subMenu, Recipe recipe) {
        subMenu.deleteRecipe(recipe);
        return update(subMenu);
    }
    public Optional<SubMenu> findByIdWithRecipes(Long id) {
        return  subMenuRepository.findByIdWithRecipes(id);
   }
}
