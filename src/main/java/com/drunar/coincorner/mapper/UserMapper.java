package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.dto.UserReadDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Mapper(componentModel = "spring", uses = {WalletMapper.class})
@Component
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.wallets", target = "walletsDTO")
    UserReadDTO userToUserReadDTO(User user);


    @Mapping(target = "wallets", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "notes", ignore = true)
    User userDTOtoUser(UserReadDTO userReadDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "wallets", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(source = "rawPassword", target = "password")
    @Mapping(target = "notes", ignore = true)
    User userCreateEditDTOtoUser(UserCreateEditDTO userCreateEditDTO);

    @AfterMapping
    default void mapImage(UserCreateEditDTO dto, @MappingTarget User user) {
        Optional.ofNullable(dto.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
    }

}
