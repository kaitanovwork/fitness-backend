package kz.kaitanov.fitnessbackend.repository.dto;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.dto.response.ProductResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface ProductResponseDtoRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.ProductResponseDto(
            p.id, p.name, p.calorie, p.protein, p.fat, p.carbohydrate)
            FROM Product p
    """)
    List<ProductResponseDto> getProductResponseDtoList();

    @Query ("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.ProductResponseDto(
            p.id, p.name, p.calorie, p.protein, p.fat, p.carbohydrate)
            FROM Product p 
            WHERE p.id = :productId
    """)
    Optional<ProductResponseDto> getProductResponseDtoById(@Param("productId") Long id);

    @Query ("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.ProductResponseDto(
            p.id, p.name, p.calorie, p.protein, p.fat, p.carbohydrate)
            FROM Product p
            WHERE p.name = :productName
    """)
    Optional<ProductResponseDto> getProductResponseDtoByName(@Param("productName") String name);


}
