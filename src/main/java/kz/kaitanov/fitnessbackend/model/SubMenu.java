package kz.kaitanov.fitnessbackend.model;


import kz.kaitanov.fitnessbackend.model.enums.ProgramType;
import kz.kaitanov.fitnessbackend.model.enums.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Table(name = "submenu")
public class SubMenu {

    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgramType programType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WeekDay weekDay;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "submenus_recipes",
            joinColumns = @JoinColumn(name = "submenus_id"),
            inverseJoinColumns = @JoinColumn(name = "resipes_list_id"))

    private List<Recipe> recipesList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubMenu)) return false;
        SubMenu subMenu = (SubMenu) o;
        return programType == subMenu.programType && weekDay == subMenu.weekDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(programType, weekDay);
    }
}
