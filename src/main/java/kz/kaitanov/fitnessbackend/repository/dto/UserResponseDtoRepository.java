package kz.kaitanov.fitnessbackend.repository.dto;

import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserResponseDtoRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    @Query("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto(u.id, u.username, u.firstName, u.lastName, u.email, u.phone, u.age, u.gender)
            FROM User u
            """)
    List<UserResponseDto> getUserResponseDtoList();

    @Query("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto(u.id, u.username, u.firstName, u.lastName, u.email, u.phone, u.age, u.gender)
            FROM User u
            WHERE u.id = :userId
            """)
    Optional<UserResponseDto> getUserResponseDtoById(@Param("userId") Long userId);

    @Query("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto(u.id, u.username, u.firstName, u.lastName, u.email, u.phone, u.age, u.gender)
            FROM User u
            WHERE u.username = :username
            """)
    Optional<UserResponseDto> getUserResponseDtoByUsername(@Param("username") String username);

    @Query("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto(u.id, u.username, u.firstName, u.lastName, u.email, u.phone, u.age, u.gender)
            FROM User u
            WHERE u.email = :email
            """)
    Optional<UserResponseDto> getUserResponseDtoByEmail(@Param("email") String email);

    @Query("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto(u.id, u.username, u.firstName, u.lastName, u.email, u.phone, u.age, u.gender)
            FROM User u
            WHERE u.phone = :phone
            """)
    Optional<UserResponseDto> getUserResponseDtoByPhone(@Param("phone") String phone);
}
