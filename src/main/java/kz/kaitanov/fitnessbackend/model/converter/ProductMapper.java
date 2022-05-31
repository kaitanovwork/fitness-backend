package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.dto.request.ProductPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.ProductUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.ProductResponseDto;

public class ProductMapper {

    public static Product toEntity (ProductPersistRequestDto productPersistRequestDto){
        Product product = new Product();
        product.setName(productPersistRequestDto.name());
        product.setCalorie(productPersistRequestDto.calorie());
        product.setCarbohydrate(productPersistRequestDto.carbohydrate());
        product.setProtein(productPersistRequestDto.protein());
        product.setFat(productPersistRequestDto.fat());
        return product;
    }

    public static Product toEntity (ProductUpdateRequestDto productUpdateRequestDto) {
        Product product = new Product();
        product.setId(productUpdateRequestDto.id());
        product.setName(productUpdateRequestDto.name());
        product.setCalorie(productUpdateRequestDto.calorie());
        product.setCarbohydrate(productUpdateRequestDto.carbohydrate());
        product.setProtein(productUpdateRequestDto.protein());
        product.setFat(productUpdateRequestDto.fat());
        return product;

    }

    public static ProductResponseDto toDto (Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getCalorie(),
                product.getProtein(),
                product.getFat(),
                product.getFat());
    }
}
