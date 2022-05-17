package kz.kaitanov.fitnessbackend.service.implementations.dto;

import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.repository.dto.UserResponseDtoRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.dto.UserResponseDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserResponseDtoServiceImpl implements UserResponseDtoService {

    private final UserResponseDtoRepository userResponseDtoRepository;

    @Override
    public List<UserResponseDto> findAll() {
        return userResponseDtoRepository.getUserResponseDtoList();
    }

    @Override
    public Optional<UserResponseDto> findById(Long userId) {
        return userResponseDtoRepository.getUserResponseDtoById(userId);
    }

    @Override
    public Optional<UserResponseDto> findByUsername(String username) {
        return userResponseDtoRepository.getUserResponseDtoByUsername(username);
    }

    @Override
    public Optional<UserResponseDto> findByEmail(String email) {
        return userResponseDtoRepository.getUserResponseDtoByEmail(email);
    }

    @Override
    public Optional<UserResponseDto> findByPhone(String phone) {
        return userResponseDtoRepository.getUserResponseDtoByPhone(phone);
    }
}
