package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.dto.UserCreateEditDTO;

public interface UserCopyHelper extends Mapper<UserCreateEditDTO, User>{

    User map(UserCreateEditDTO fromObject, User toObject, String uploadImage);
    User map(UserCreateEditDTO object);

}
