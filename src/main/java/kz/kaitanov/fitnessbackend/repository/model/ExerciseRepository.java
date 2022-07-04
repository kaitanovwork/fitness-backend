package kz.kaitanov.fitnessbackend.repository.model;

import kz.kaitanov.fitnessbackend.model.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
            FROM Exercise e
            WHERE e.name = :name 
            """)
    boolean existsByName(String name);
}
