package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.SubMenu;
import kz.kaitanov.fitnessbackend.model.dto.request.subMenu.SubMenuPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.subMenu.SubMenuUpdateRequestDto;

public final class SubMenuMapper {

    public static SubMenu toEntity(SubMenuPersistRequestDto dto) {
        SubMenu subMenu = new SubMenu();
        subMenu.setProgramType(dto.programType());
        subMenu.setWeekDay(dto.weekDay());
        return subMenu;
    }

    public static SubMenu updateSubMenu(SubMenu subMenu, SubMenuUpdateRequestDto dto) {
        subMenu.setProgramType(dto.programType());
        subMenu.setWeekDay(dto.weekDay());
        return subMenu;
    }
}
