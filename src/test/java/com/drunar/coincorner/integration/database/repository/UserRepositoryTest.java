package com.drunar.coincorner.integration.database.repository;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.repository.UserRepository;
import com.drunar.coincorner.database.filter.UserFilter;
import com.drunar.coincorner.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void checkPageable() {
        PageRequest pageable = PageRequest.of(1, 2, Sort.by("id"));
        Page<User> page = userRepository.findAllBy(pageable);
        page.forEach(user -> System.out.println(user.getId()));

        while (page.hasNext()) {
            page = userRepository.findAllBy(page.nextPageable());
            page.forEach(user -> System.out.println(user.getId()));
        }
        assertThat(page).hasSize(2);
    }

    @Test
    void checkCustomImplementation() {
        UserFilter filter = new UserFilter(null, "i", null);
        List<User> users = userRepository.findAllByFilter(filter);
        assertThat(users).hasSize(4);
        assertThat(users).as("The size of the list should not be 5").isNotEqualTo(5);

    }

}
