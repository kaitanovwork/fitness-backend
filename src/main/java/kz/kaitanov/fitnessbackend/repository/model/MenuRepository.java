package kz.kaitanov.fitnessbackend.repository.model;

import kz.kaitanov.fitnessbackend.model.Menu;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Menu r
            WHERE r.id = :id
            """)
    boolean existsById(Long id);

    @Query("""
            SELECT r
            FROM Menu r JOIN FETCH r.subMenus
            WHERE r.id = :id
            """)
    Optional<Menu> findByIdWithSubMenus(Long id);
}
