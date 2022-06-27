package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.SubMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AbstractService<T, PK> {

    Optional<T> findById(PK id);

    Page<SubMenu> findAll(Pageable pageable);

    T save(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(PK id);

    boolean existsById(PK id);
}
