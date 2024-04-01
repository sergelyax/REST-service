package dao;

import config.DatabaseConfig;
import entity.Post;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


@Testcontainers
class PostDAOTest {

    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>("postgres:16.2")
                    .withDatabaseName("rest_service_db")
                    .withUsername("postgres")
                    .withPassword("123")
                    .withInitScript("schema.sql");

    private static PostDAO postDAO;

    @BeforeAll
    static void setUp() {
        DatabaseConfig databaseConfig = DatabaseConfig.getInstance();
        // Переопределение параметров подключения к базе данных для использования контейнера
        databaseConfig.overrideConnectionUrl(postgresqlContainer.getJdbcUrl());
        databaseConfig.overrideUsername(postgresqlContainer.getUsername());
        databaseConfig.overridePassword(postgresqlContainer.getPassword());

        postDAO = new PostDAO();
    }

    @AfterAll
    static void tearDown() {
        postgresqlContainer.stop();
    }

    @Test
    void getPostByIdTest() throws SQLException {
        Post post = postDAO.getPostById(1);

        assertNotNull(post);
        assertEquals(1, post.getId());
    }

    @Test
    void createPostTest() throws SQLException {
        Post newPost = new Post();
        newPost.setUserId(1);
        newPost.setTitle("New Post");
        newPost.setContent("Content of the new post");

        postDAO.createPost(newPost);

    }

    @Test
    void updatePostTest() throws SQLException {
        Post postToUpdate = postDAO.getPostById(1);
        String updatedTitle = "Updated Title";
        String updatedContent = "Updated content";
        postToUpdate.setTitle(updatedTitle);
        postToUpdate.setContent(updatedContent);

        postDAO.updatePost(postToUpdate);

        Post updatedPost = postDAO.getPostById(1);

        assertNotNull(updatedPost);
        assertEquals(updatedTitle, updatedPost.getTitle(), "Updated Title");
        assertEquals(updatedContent, updatedPost.getContent(), "Updated content");
    }

    @Test
    void deletePostTest() throws SQLException {
        Post postToDelete = new Post();
        postToDelete.setUserId(1);
        postToDelete.setTitle("Post to Delete");
        postToDelete.setContent("This post will be deleted");
        postDAO.createPost(postToDelete);

        int postId = postToDelete.getId();

        assertNotNull(postDAO.getPostById(postId));

        postDAO.deletePost(postId);

        assertNull(postDAO.getPostById(postId));
    }

}
