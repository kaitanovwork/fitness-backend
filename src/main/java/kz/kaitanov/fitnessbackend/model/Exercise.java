package kz.kaitanov.fitnessbackend.model;

import kz.kaitanov.fitnessbackend.model.enums.Area;
import kz.kaitanov.fitnessbackend.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @ElementCollection
    @OrderColumn(name = "id")
    @CollectionTable(name="muscle_groups",
                    joinColumns = @JoinColumn(referencedColumnName = "id", name = "exercises_id"))
    @Column(name = "muscle_group")
    private List<String> muscleGroups = new ArrayList<>();

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer repeatCount;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer approachCount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Area area;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(name, exercise.name) && Objects.equals(muscleGroups, exercise.muscleGroups) && Objects.equals(repeatCount, exercise.repeatCount) && Objects.equals(approachCount, exercise.approachCount) && area == exercise.area;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, muscleGroups, repeatCount, approachCount, area);
    }
}
