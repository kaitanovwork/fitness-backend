package kz.kaitanov.fitnessbackend.repository.dto;

import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserResponseDtoRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto(u.id, u.username, u.firstName, u.lastName, u.email, u.phone, u.age, u.gender,u.coach)
            FROM User u
            WHERE u.id = :userId
            """)
    Optional<UserResponseDto> findDtoById(@Param("userId") Long userId);

    @Query("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto(u.id, u.username, u.firstName, u.lastName, u.email, u.phone, u.age, u.gender,u.coach)
            FROM User u
            WHERE u.username = :username
            """)
    Optional<UserResponseDto> findDtoByUsername(@Param("username") String username);

    @Query("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto(u.id, u.username, u.firstName, u.lastName, u.email, u.phone, u.age, u.gender,u.coach)
            FROM User u
            WHERE u.email = :email
            """)
    Optional<UserResponseDto> findDtoByEmail(@Param("email") String email);

    @Query("""
            SELECT new kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto(u.id, u.username, u.firstName, u.lastName, u.email, u.phone, u.age, u.gender,u.coach)
            FROM User u
            WHERE u.phone = :phone
            """)
    Optional<UserResponseDto> findDtoByPhone(@Param("phone") String phone);

    @Query("""
    SELECT COUNT (u.id)
    FROM User u
    WHERE u.role.name = 'USER'
    """)
    Integer getUsersWithUserRole();
}
