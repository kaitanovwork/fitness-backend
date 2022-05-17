package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Role;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;

import java.util.Optional;

public interface RoleService extends AbstractService<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
