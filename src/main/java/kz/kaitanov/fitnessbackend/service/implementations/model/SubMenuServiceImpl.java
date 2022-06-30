package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import kz.kaitanov.fitnessbackend.repository.model.SubMenuRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.SubMenuService;
import kz.kaitanov.fitnessbackend.web.config.util.ApiValidationUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SubMenuServiceImpl extends AbstractServiceImpl<SubMenu, Long> implements SubMenuService {

    private final SubMenuRepository subMenuRepository;

    public SubMenuServiceImpl(SubMenuRepository subMenuRepository) {
        super(subMenuRepository);
        this.subMenuRepository = subMenuRepository;
    }

    @Override
    public SubMenu addRecipeToSubMenu(SubMenu subMenu, Recipe recipe) {
        subMenu.addRecipe(recipe);
        return update(subMenu);
    }

    @Override
    public SubMenu deleteRecipeFromSubMenu(SubMenu subMenu, Recipe recipe) {
        subMenu.deleteRecipe(recipe);
        return update(subMenu);
    }

    @Override
    public Optional<SubMenu> findByIdWithRecipes(Long id) {
        return subMenuRepository.findByIdWithRecipes(id);
    }

    @Override
    public List<SubMenu> findByIds(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        List<SubMenu> subMenuList = subMenuRepository.findAllById(idList);
        ApiValidationUtil.requireTrue(subMenuList.size() > 0, String.format(" SubMenu with such id's not found"));
        return subMenuList;
    }
}
