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
@Table(name = "products")
public class Product {
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(nullable = false, unique = true)
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

    @ManyToMany(mappedBy = "products")
    List<Recipe> recipes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(calorie, product.calorie) &&
                Objects.equals(protein, product.protein) &&
                Objects.equals(fat, product.fat) &&
                Objects.equals(carbohydrate, product.carbohydrate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, calorie, protein, fat, carbohydrate); }
}
