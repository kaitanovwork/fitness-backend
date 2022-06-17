package kz.kaitanov.fitnessbackend.repository.model;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubMenuRepository extends JpaRepository<SubMenu, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
            FROM SubMenu p
            WHERE p.id = :id
            """)
    boolean existsById(Long id);

    Optional<SubMenu> findById(Long id);
}
