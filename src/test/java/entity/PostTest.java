package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    void testPostSetAndGet() {
        User user = new User();
        user.setId(100);
        user.setUsername("AstonMan");
        user.setEmail("Aston.Man@example.com");

        Post post = new Post();
        post.setId(1);
        post.setUser(user); // Используем setUser вместо setUserId
        post.setTitle("Test Title");
        post.setContent("Test Content");

        // Проверка
        assertEquals(1, post.getId(), "ID не соответствует установленному значению.");
        assertEquals(user, post.getUser(), "User объект не соответствует установленному значению."); // Изменили на проверку User
        assertEquals("Test Title", post.getTitle(), "Title не соответствует установленному значению.");
        assertEquals("Test Content", post.getContent(), "Content не соответствует установленному значению.");
    }


    @Test
    void testPostConstructor() {
        User user = new User();
        user.setId(200);
        user.setUsername("AstonMan");
        user.setEmail("Aston.Man@example.com");

        Post post = new Post(2, user, "Another Title", "Another Content");

        assertEquals(2, post.getId(), "ID не соответствует ожидаемому.");
        assertEquals(user, post.getUser(), "User объект не соответствует ожидаемому.");
        assertEquals("Another Title", post.getTitle(), "Title не соответствует ожидаемому.");
        assertEquals("Another Content", post.getContent(), "Content не соответствует ожидаемому.");
    }
}