package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.dto.request.ProductPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.ProductUpdateRequestDto;

public class ProductMapper {

    public static Product toEntity (ProductPersistRequestDto dto){
        Product product = new Product();
        product.setName(dto.name());
        product.setCalorie(dto.calorie());
        product.setCarbohydrate(dto.carbohydrate());
        product.setProtein(dto.protein());
        product.setFat(dto.fat());
        return product;
    }
}
