    package dao;

    import config.DatabaseConfig;
    import entity.User;
    import org.junit.jupiter.api.*;
    import org.testcontainers.containers.PostgreSQLContainer;
    import org.testcontainers.junit.jupiter.Container;
    import org.testcontainers.junit.jupiter.Testcontainers;

    import java.sql.SQLException;

    import static org.junit.Assert.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;
    import static org.junit.jupiter.api.Assertions.assertNull;

    @Testcontainers
    class UserDAOTest {

        @Container
        private static final PostgreSQLContainer<?> postgresqlContainer =
                new PostgreSQLContainer<>("postgres:16.2")
                        .withDatabaseName("rest_service_db")
                        .withUsername("postgres")
                        .withPassword("123")
                        .withInitScript("schema.sql");

        private static UserDAO userDAO;

        @BeforeAll
        static void setUp() {
            DatabaseConfig databaseConfig = DatabaseConfig.getInstance();
            databaseConfig.overrideConnectionUrl(postgresqlContainer.getJdbcUrl());
            databaseConfig.overrideUsername(postgresqlContainer.getUsername());
            databaseConfig.overridePassword(postgresqlContainer.getPassword());

            userDAO = new UserDAO();
        }

        @AfterAll
        static void tearDown() {
            postgresqlContainer.stop(); }

        @Test
        void getUserByIdTest() throws SQLException {
            User user = userDAO.getUserById(2);
            assertEquals(2, user.getId());
        }


        @Test
        void updateUserTest() throws SQLException {
            User userToUpdate = userDAO.getUserById(1);
            userToUpdate.setUsername("updatedName");
            userDAO.updateUser(userToUpdate);

            User updatedUser = userDAO.getUserById(1);
            assertNotNull(updatedUser, "User should exist after update");
            assertEquals("updatedName", updatedUser.getUsername());
        }

        @Test
        void deleteUserTest() throws SQLException {
            int userIdToDelete = 1;
            userDAO.deleteUser(userIdToDelete);

            User userAfterDeletion = userDAO.getUserById(userIdToDelete);
            assertNull(userAfterDeletion, "User should be deleted");
        }
    }
