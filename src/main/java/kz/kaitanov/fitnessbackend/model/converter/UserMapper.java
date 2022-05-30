package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.dto.request.UserPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.UserUpdateProfileRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.UserUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;

public final class UserMapper {

    public static User toEntity(UserPersistRequestDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setAge(dto.age());
        user.setGender(dto.gender());
        return user;
    }

    public static User toEntity(UserUpdateRequestDto dto) {
        User user = new User();
        user.setId(dto.id());
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setAge(dto.age());
        user.setGender(dto.gender());
        return user;
    }

    public static User toEntity(User user, UserUpdateProfileRequestDto dto) {
        user.setId(dto.id());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setAge(dto.age());
        user.setGender(dto.gender());
        return user;
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getAge(),
                user.getGender()
        );
    }
}
