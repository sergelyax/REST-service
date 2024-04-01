package service;

import dto.UserDTO;
import entity.User;
import dao.UserDAO;
import mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByIdTest() {
        User user = new User();
        user.setId(1);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);

        when(userDAO.getUserById(1)).thenReturn(user);
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(userDAO).getUserById(1);
        verify(userMapper).userToUserDTO(user);
    }

    @Test
    void getAllUsersTest() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userDAO.getAllUsers()).thenReturn(users);

        List<UserDTO> userDTOs = Arrays.asList(new UserDTO(), new UserDTO());
        when(userMapper.userToUserDTO(any(User.class))).thenReturn(userDTOs.get(0), userDTOs.get(1));

        List<UserDTO> results = userService.getAllUsers();

        assertNotNull(results);
        assertEquals(2, results.size());
        verify(userDAO).getAllUsers();
        verify(userMapper, times(users.size())).userToUserDTO(any(User.class));
    }

    @Test
    void createUserTest() {
        UserDTO userDTO = new UserDTO();
        User user = new User();

        try (MockedStatic<UserMapper> mockedStatic = Mockito.mockStatic(UserMapper.class)) {
            mockedStatic.when(() -> UserMapper.userDTOToUser(userDTO)).thenReturn(user);
            doNothing().when(userDAO).createUser(user);

            userService.createUser(userDTO);

            mockedStatic.verify(() -> UserMapper.userDTOToUser(userDTO));
            verify(userDAO).createUser(user);
        }
    }

    @Test
    void updateUserTest() {
        UserDTO userDTO = new UserDTO();
        User user = new User();

        try (MockedStatic<UserMapper> mockedStatic = Mockito.mockStatic(UserMapper.class)) {
            mockedStatic.when(() -> UserMapper.userDTOToUser(userDTO)).thenReturn(user);
            doNothing().when(userDAO).updateUser(user);

            userService.updateUser(userDTO);

            mockedStatic.verify(() -> UserMapper.userDTOToUser(userDTO));
            verify(userDAO).updateUser(user);
        }
    }

    @Test
    void deleteUserTest() {
        int userId = 1;
        doNothing().when(userDAO).deleteUser(userId);

        userService.deleteUser(userId);

        verify(userDAO).deleteUser(userId);
    }
}
