package kz.kaitanov.fitnessbackend.model;

import kz.kaitanov.fitnessbackend.model.enums.Area;
import kz.kaitanov.fitnessbackend.model.enums.Category;
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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @NotBlank
    @Column(nullable = false)
    private String muscleGroup;

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
        return Objects.equals(name, exercise.name) && Objects.equals(muscleGroup, exercise.muscleGroup) && Objects.equals(repeatCount, exercise.repeatCount) && Objects.equals(approachCount, exercise.approachCount) && area == exercise.area;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, muscleGroup, repeatCount, approachCount, area);
    }
}
