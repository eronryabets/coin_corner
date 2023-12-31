package com.drunar.coincorner.integration;

import com.drunar.coincorner.integration.annotation.IT;
import com.drunar.coincorner.util.WithMockCustomUser;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
@Sql({
        "classpath:sql/data.sql"
})
//@WithMockUser(username="test@gmail.com",
//        password = "test",
//        authorities = {"ADMIN","USER"})
@WithMockCustomUser(
        username = "test@gmail.com",
        id = 1L,
        password = "password",
        roles = {"USER","ADMIN"})

public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:15.3");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}