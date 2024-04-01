package dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PostDTOTest {

    @Test
    void testPostDTOSetAndGet() {
        PostDTO post = new PostDTO();
        post.setId(1);
        post.setUserId(10);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        assertEquals(1, post.getId(), "ID не соответствует установленному значению.");
        assertEquals(10, post.getUserId(), "UserID не соответствует установленному значению.");
        assertEquals("Test Title", post.getTitle(), "Title не соответствует установленному значению.");
        assertEquals("Test Content", post.getContent(), "Content не соответствует установленному значению.");
    }

    @Test
    void testPostDTOConstructor() {
        PostDTO post = new PostDTO(2, 20, "Another Title", "Another Content");

        assertEquals(2, post.getId(), "ID не соответствует ожидаемому.");
        assertEquals(20, post.getUserId(), "UserID не соответствует ожидаемому.");
        assertEquals("Another Title", post.getTitle(), "Title не соответствует ожидаемому.");
        assertEquals("Another Content", post.getContent(), "Content не соответствует ожидаемому.");
    }
}
