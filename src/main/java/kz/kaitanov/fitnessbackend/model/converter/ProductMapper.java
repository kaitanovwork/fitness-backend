package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.dto.request.product.ProductPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.product.ProductUpdateNameRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.product.ProductUpdateRequestDto;

public class ProductMapper {

    public static Product toEntity(ProductPersistRequestDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setCalorie(dto.calorie());
        product.setCarbohydrate(dto.carbohydrate());
        product.setProtein(dto.protein());
        product.setFat(dto.fat());
        return product;
    }

    public static Product updateProduct(Product product, ProductUpdateRequestDto dto) {
        product.setCalorie(dto.calorie());
        product.setProtein(dto.protein());
        product.setFat(dto.fat());
        product.setCarbohydrate(dto.carbohydrate());
        return product;
    }

    public static Product updateName(Product product, ProductUpdateNameRequestDto dto) {
        product.setName(dto.name());
        return product;
    }
}
