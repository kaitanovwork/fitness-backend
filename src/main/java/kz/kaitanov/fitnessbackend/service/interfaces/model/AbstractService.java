package kz.kaitanov.fitnessbackend.service.interfaces.model;

import java.util.List;
import java.util.Optional;

public interface AbstractService<T, PK> {

    Optional<T> findById(PK id);

    List<T> findAll();

    T save(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(PK id);

    boolean existsById(PK id);
}
