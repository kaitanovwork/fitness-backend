package kz.kaitanov.fitnessbackend.service.implementations.dto;

import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.repository.model.dto.UserResponseDtoRepository;
import kz.kaitanov.fitnessbackend.repository.model.UserRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.dto.UserResponseDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserResponseDtoServiceImpl implements UserResponseDtoService {

    private final UserResponseDtoRepository userResponseDtoRepository;
    private final UserRepository userRepository;

    @Override
    public Page<UserResponseDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserMapper::toDto);
    }

    @Override
    public Optional<UserResponseDto> findById(Long userId) {
        return userResponseDtoRepository.findDtoById(userId);
    }

    @Override
    public Optional<UserResponseDto> findByUsername(String username) {
        return userResponseDtoRepository.findDtoByUsername(username);
    }

    @Override
    public Optional<UserResponseDto> findByEmail(String email) {
        return userResponseDtoRepository.findDtoByEmail(email);
    }

    @Override
    public Optional<UserResponseDto> findByPhone(String phone) {
        return userResponseDtoRepository.findDtoByPhone(phone);
    }
}
