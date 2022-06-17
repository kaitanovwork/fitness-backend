package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.SubMenu;
import kz.kaitanov.fitnessbackend.repository.model.SubMenuRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.SubMenuService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubMenuServiceImp extends AbstractServiceImpl<SubMenu, Long> implements SubMenuService {

    private final SubMenuRepository subMenuRepository;

    public SubMenuServiceImp(JpaRepository<SubMenu, Long> repository, SubMenuRepository subMenuRepository) {
        super(repository);
        this.subMenuRepository = subMenuRepository;
    }


    @Override
    public boolean existsById(Long id) {
        return subMenuRepository.existsById(id);
    }

    @Override
    public Optional<SubMenu> findById(Long id) {
        return subMenuRepository.findById(id);
    }
}
