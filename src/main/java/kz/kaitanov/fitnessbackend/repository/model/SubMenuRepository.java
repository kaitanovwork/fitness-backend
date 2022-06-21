package kz.kaitanov.fitnessbackend.repository.model;

import kz.kaitanov.fitnessbackend.model.SubMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubMenuRepository extends JpaRepository<SubMenu, Long> {

    @Query("""
            SELECT s
            FROM SubMenu s LEFT JOIN FETCH s.recipes
            WHERE s.id = :id
            """)
    Optional<SubMenu> findByIdWithRecipes(Long id);
}