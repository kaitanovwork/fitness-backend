package kz.kaitanov.fitnessbackend.repository.model;

import kz.kaitanov.fitnessbackend.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("""
            SELECT m
            FROM Menu m LEFT JOIN FETCH m.subMenus
            WHERE m.id = :id
            """)
    Optional<Menu> findByIdWithSubMenus(Long id);
}
