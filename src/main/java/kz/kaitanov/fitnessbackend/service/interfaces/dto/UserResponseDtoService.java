package kz.kaitanov.fitnessbackend.service.interfaces.dto;

import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface UserResponseDtoService {

    List<UserResponseDto> findAll();

    Page<UserResponseDto> findAllPaginated(PageRequest pageRequest);

    Optional<UserResponseDto> findById(Long userId);

    Optional<UserResponseDto> findByUsername(String username);

    Optional<UserResponseDto> findByEmail(String email);

    Optional<UserResponseDto> findByPhone(String phone);
}
