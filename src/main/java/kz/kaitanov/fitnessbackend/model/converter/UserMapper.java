package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserRegistrationRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateEmailRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdatePasswordRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdatePhoneRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateProfileRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;

public final class UserMapper {

    public static User toEntity(UserRegistrationRequestDto dto) {
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
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setAge(dto.age());
        user.setGender(dto.gender());
        return user;
    }

    public static User updateUser(User user, UserUpdateRequestDto dto) {
        user.setUsername(dto.username());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setAge(dto.age());
        user.setGender(dto.gender());
        return user;
    }

    public static User updateProfile(User user, UserUpdateProfileRequestDto dto) {
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setAge(dto.age());
        user.setGender(dto.gender());
        return user;
    }

    public static User updatePassword(User user, UserUpdatePasswordRequestDto dto) {
        user.setPassword(dto.password());
        return user;
    }

    public static User updateEmail(User user, UserUpdateEmailRequestDto dto) {
        user.setEmail(dto.email());
        return user;
    }

    public static User updatePhone(User user, UserUpdatePhoneRequestDto dto) {
        user.setPhone(dto.phone());
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
                user.getGender(),
                user.getCoach()
        );
    }
}
