package mapper;

import dto.UserDTO;
import entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void userToUserDTOConversion() {
        // Создаем экземпляр User
        User user = new User();
        user.setId(1);
        user.setUsername("userTest");
        user.setEmail("user@test.com");

        UserDTO userDTO = new UserMapper().userToUserDTO(user);

        assertNotNull(userDTO, "The conversion should not return null.");
        assertEquals(user.getId(), userDTO.getId(), "IDs should match after conversion.");
        assertEquals(user.getUsername(), userDTO.getUsername(), "Usernames should match after conversion.");
        assertEquals(user.getEmail(), userDTO.getEmail(), "Emails should match after conversion.");
    }

    @Test
    void userDTOToUserConversion() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(2);
        userDTO.setUsername("anotherUser");
        userDTO.setEmail("another@test.com");

        User user = UserMapper.userDTOToUser(userDTO);

        assertNotNull(user, "The conversion should not return null.");
        assertEquals(userDTO.getId(), user.getId(), "IDs should match after conversion.");
        assertEquals(userDTO.getUsername(), user.getUsername(), "Usernames should match after conversion.");
        assertEquals(userDTO.getEmail(), user.getEmail(), "Emails should match after conversion.");
    }

    @Test
    void conversionWithNullShouldReturnNull() {
        assertNull(new UserMapper().userToUserDTO(null), "Converting null User should return null.");
        assertNull(UserMapper.userDTOToUser(null), "Converting null UserDTO should return null.");
    }
}
