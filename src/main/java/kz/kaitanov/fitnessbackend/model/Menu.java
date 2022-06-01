package kz.kaitanov.fitnessbackend.model;


import kz.kaitanov.fitnessbackend.model.enums.ProgramType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "menu")
public class Menu {

    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProgramType programType;

    @ManyToMany
    @JoinTable(name = "menus_submenus",
            joinColumns = @JoinColumn(name = "menu_list_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_menu_list_id"))
    private List<SubMenu> subMenuList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        Menu menu = (Menu) o;
        return programType == menu.programType && subMenuList.equals(menu.subMenuList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(programType, subMenuList);
    }
}

