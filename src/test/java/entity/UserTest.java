package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserSetAndGet() {
        // Инициализация
        User user = new User();
        user.setId(1);
        user.setUsername("AstonMan");
        user.setEmail("Aston.Man@example.com");

        // Проверка
        assertEquals(1, user.getId(), "ID не соответствует установленному значению.");
        assertEquals("AstonMan", user.getUsername(), "Username не соответствует установленному значению.");
        assertEquals("Aston.Man@example.com", user.getEmail(), "Email не соответствует установленному значению.");
    }

    @Test
    void testUserConstructor() {
        // Инициализация с использованием параметризированного конструктора
        User user = new User(2, "AstonMan", "Aston.Man@example.com", "1234321");

        // Проверка
        assertEquals(2, user.getId(), "ID не соответствует ожидаемому.");
        assertEquals("AstonMan", user.getUsername(), "Username не соответствует ожидаемому.");
        assertEquals("Aston.Man@example.com", user.getEmail(), "Email не соответствует ожидаемому.");
        assertEquals("1234321", user.getPassword(), "Неправильный пароль." );
    }
}
