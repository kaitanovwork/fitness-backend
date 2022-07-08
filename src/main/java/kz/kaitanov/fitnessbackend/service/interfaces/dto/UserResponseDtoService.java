package kz.kaitanov.fitnessbackend.service.interfaces.dto;

import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserResponseDtoService {

    Page<UserResponseDto> findAll(Pageable pageable);

    Optional<UserResponseDto> findById(Long userId);

    Optional<UserResponseDto> findByUsername(String username);

    Optional<UserResponseDto> findByEmail(String email);

    Optional<UserResponseDto> findByPhone(String phone);
    Integer getUsersWithUserRoleCount();
}
