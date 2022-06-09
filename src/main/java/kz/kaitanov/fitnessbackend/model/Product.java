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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(calorie, product.calorie) && Objects.equals(protein, product.protein) && Objects.equals(fat, product.fat) && Objects.equals(carbohydrate, product.carbohydrate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, calorie, protein, fat, carbohydrate);
    }
}
