package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Role;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;

public interface RoleService extends AbstractService<Role, Long> {

    Role findByName(RoleName name);
}
