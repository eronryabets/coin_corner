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

import java.time.LocalDate;
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
    void checkUserFilterFirstname() {
        UserFilter filter = new UserFilter("john", null, null, null,null, null, null);
        List<User> users = userRepository.findAllByFilter(filter);
        assertThat(users).hasSize(1);
    }

    @Test
    void checkUserFilterLastname() {
        UserFilter filter = new UserFilter(null, "i", null,null,null, null, null);
        List<User> users = userRepository.findAllByFilter(filter);
        assertThat(users).hasSize(4);
        assertThat(users).as("The size of the list should not be 5").isNotEqualTo(5);

    }

    @Test
    void checkUserFilterBirthDate() {
        LocalDate date = LocalDate.of(1990, 1, 1);

        UserFilter filterIn = new UserFilter(null, null,
                date,null,null, null, null);
        List<User> usersIn = userRepository.findAllByFilter(filterIn);

        UserFilter filterAfter = new UserFilter(null, null,
                null,null,date,null,null);
        List<User> usersAfter = userRepository.findAllByFilter(filterAfter);

        UserFilter filterBefore = new UserFilter(null, null,
                null,date,null, null, null);
        List<User> usersBefore = userRepository.findAllByFilter(filterBefore);

        assertThat(usersIn).hasSize(1);
        assertThat(usersAfter).hasSize(5);
        assertThat(usersBefore).hasSize(4);

    }

    @Test
    void checkUserFilterFromDateToDate() {
        LocalDate dateStart = LocalDate.of(1980, 1, 1);
        LocalDate dateEnd = LocalDate.of(1990, 1, 1);

        UserFilter filter = new UserFilter(null, null,
                null,null,null, dateStart, dateEnd);
        List<User> users = userRepository.findAllByFilter(filter);

        assertThat(users).hasSize(5);

    }

}
