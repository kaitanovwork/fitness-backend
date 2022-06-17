package kz.kaitanov.fitnessbackend.service.interfaces.model;


import kz.kaitanov.fitnessbackend.model.SubMenu;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface SubMenuService extends AbstractService<SubMenu, Long>{

    boolean existsById(Long id);

    Optional<SubMenu> findById(Long id);
}
