package config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
public class DatabaseConfigTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16.2")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @BeforeAll
    static void setUp() throws Exception {
        System.setProperty("db.url", postgresContainer.getJdbcUrl());
        System.setProperty("db.user", postgresContainer.getUsername());
        System.setProperty("db.password", postgresContainer.getPassword());
    }

    @AfterAll
    static void tearDown() {
        System.clearProperty("db.url");
        System.clearProperty("db.user");
        System.clearProperty("db.password");
    }

    @Test
    void testGetConnection() throws SQLException {
        try (Connection connection = DatabaseConfig.getInstance().getConnection()) {
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        }
    }
}
