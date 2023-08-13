package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.dto.UserReadDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {WalletMapper.class})
@Component
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.wallets", target = "walletsDTO")
    UserReadDTO userToUserReadDTO(User user);


    @Mapping(target = "wallets", ignore = true)
    User userDTOtoUser(UserReadDTO userReadDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "wallets", ignore = true)
    User userCreateEditDTOtoUser(UserCreateEditDTO userCreateEditDTO);

    default User userCreateEditDTOCopyToUser(UserCreateEditDTO object, User user) {
        user.setEmail(object.getEmail());
        user.setUsername(object.getUsername());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        return user;
    }
}
