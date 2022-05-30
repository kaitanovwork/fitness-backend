package kz.kaitanov.fitnessbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recipes")
public class Recipe {

    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer calorie;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer protein;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer fat;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer carbohydrate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name) && Objects.equals(calorie, recipe.calorie) && Objects.equals(protein, recipe.protein) && Objects.equals(fat, recipe.fat) && Objects.equals(carbohydrate, recipe.carbohydrate);
    }
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipes_products",
            joinColumns = @JoinColumn,
            inverseJoinColumns = @JoinColumn
    )
    List<Product> products;


    @Override
    public int hashCode() {
        return Objects.hash(name, calorie, protein, fat, carbohydrate);
    }
}
