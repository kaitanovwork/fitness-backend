package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.Menu;
import kz.kaitanov.fitnessbackend.model.dto.request.menu.MenuPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.menu.MenuUpdateRequestDto;

public class MenuMapper {

    public static Menu toEntity(MenuPersistRequestDto dto) {
        Menu menu = new Menu();
        menu.setProgramType(dto.programType());
        return menu;
    }

    public static Menu updateMenu(Menu menu, MenuUpdateRequestDto dto) {
        menu.setProgramType(dto.programType());
        return menu;
    }
}
