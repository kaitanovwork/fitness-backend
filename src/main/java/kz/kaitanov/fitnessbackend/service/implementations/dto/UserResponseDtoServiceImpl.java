package kz.kaitanov.fitnessbackend.service.implementations.dto;

import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.repository.dto.UserResponseDtoRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.dto.UserResponseDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserResponseDtoServiceImpl implements UserResponseDtoService {

    private final UserResponseDtoRepository userResponseDtoRepository;

    @Override
    public List<UserResponseDto> findAll() {
        return userResponseDtoRepository.getUserResponseDtoList();
    }

    @Override
    public Page<UserResponseDto> findAllPaginated(PageRequest pageRequest) {
        Page<User> pageResponse = Optional.of(userResponseDtoRepository.findAll(pageRequest))
                .orElseThrow(() -> new IllegalArgumentException("No users found"));


        return new PageImpl<>(pageResponse.stream()
                .map(userResponseDtoRepository -> createUserResponseDto(Optional.ofNullable(userResponseDtoRepository)))
                .collect(Collectors.toList()));
    }


    private static UserResponseDto createUserResponseDto(Optional<User> usersOptional) {
        User user = usersOptional.get();
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getAge(),
                user.getGender());
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
