package com.drunar.coincorner.database.repository.filter;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.filter.UserFilter;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter filter);

}
