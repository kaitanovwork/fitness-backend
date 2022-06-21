package kz.kaitanov.fitnessbackend.repository.model;

import kz.kaitanov.fitnessbackend.model.SubMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface SubMenuRepository extends JpaRepository<SubMenu, Long> {

    @Query("""
            SELECT sm
            FROM SubMenu sm JOIN FETCH sm.recipes
            WHERE sm.id = :id
            
            """)
    Optional<SubMenu> findByIdWithRecipes(Long id);
}