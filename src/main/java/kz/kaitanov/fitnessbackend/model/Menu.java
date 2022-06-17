package kz.kaitanov.fitnessbackend.model;

import kz.kaitanov.fitnessbackend.model.enums.ProgramType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgramType programType;

    @ManyToMany
    @JoinTable(name = "menus_submenus",
            joinColumns = @JoinColumn(name = "menus_id"),
            inverseJoinColumns = @JoinColumn(name = "submenus_id"))
    private List<SubMenu> subMenus = new ArrayList<>();

    public void addSubMenu(SubMenu subMenu) {
        if (!subMenus.contains(subMenu)) {
            subMenus.add(subMenu);
        }
    }

    public void deleteSubMenu(SubMenu subMenu) {
        subMenus.remove(subMenu);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id) && programType == menu.programType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, programType);
    }
}

