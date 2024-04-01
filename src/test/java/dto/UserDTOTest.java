package dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void testUserDTOSetAndGet() {
        UserDTO user = new UserDTO();
        user.setId(1);
        user.setUsername("TestUser");
        user.setEmail("testuser@example.com");

        assertEquals(1, user.getId(), "ID не соответствует установленному значению.");
        assertEquals("TestUser", user.getUsername(), "Username не соответствует установленному значению.");
        assertEquals("testuser@example.com", user.getEmail(), "Email не соответствует установленному значению.");
    }

    @Test
    void testUserDTOConstructor() {
        UserDTO user = new UserDTO(2, "AnotherUser", "anotheruser@example.com");

        assertEquals(2, user.getId(), "ID не соответствует ожидаемому.");
        assertEquals("AnotherUser", user.getUsername(), "Username не соответствует ожидаемому.");
        assertEquals("anotheruser@example.com", user.getEmail(), "Email не соответствует ожидаемому.");
    }
}
