package kz.kaitanov.fitnessbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer calorie;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer protein;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer fat;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer carbohydrate;

    @ManyToMany
    @JoinTable(
            name = "recipes_products",
            joinColumns = @JoinColumn(name = "recipes_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id")
    )
    List<Product> products = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name) && Objects.equals(calorie, recipe.calorie)
                && Objects.equals(protein, recipe.protein) && Objects.equals(fat, recipe.fat)
                && Objects.equals(carbohydrate, recipe.carbohydrate) && Objects.equals(description, recipe.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, calorie, protein, fat, carbohydrate);
    }
}
